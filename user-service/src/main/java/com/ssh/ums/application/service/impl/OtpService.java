package com.ssh.ums.application.service.impl;


import com.ssh.exceptions.NotFoundException;
import com.ssh.exceptions.ServiceException;
import com.ssh.ums.application.constants.Constants;
import com.ssh.ums.application.constants.LookUpConstants;
import com.ssh.ums.application.enums.DeliveryChannelEnum;
import com.ssh.ums.application.enums.FunctionalAreaEnum;
import com.ssh.ums.application.service.interfaces.IOtpService;
import com.ssh.ums.application.utility.OtpUtil;
import com.ssh.ums.domain.entity.otp.Otp;
import com.ssh.ums.domain.entity.otp.OtpRequest;
import com.ssh.ums.domain.repo.otp.OtpRepo;
import com.ssh.ums.domain.repo.otp.OtpRequestRepo;
import com.ssh.ums.dto.otp.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class OtpService implements IOtpService {
    private final OtpRepo otpRepo;
    private final OtpRequestRepo otpRequestRepo;

    @Override
    public GenerateOtpResponse generateOtpAndProcess(GenerateOtpRequest generateOtpRequest) {
        OtpRequest otpRequest = OtpRequest.builder()
                .deliveryChannel(DeliveryChannelEnum.valueOf(generateOtpRequest.getDeliveryChannel()))
                .incorrectAttempts(Constants.ZERO)
                .statusLookup(LookUpConstants.STATUS_PENDING)
                .functionalArea(FunctionalAreaEnum.valueOf(generateOtpRequest.getFunctionalArea()))
                .build();

        OtpRequest savedOtpRequest = otpRequestRepo.saveAndFlush(otpRequest);
        return generateOtpForChannels(savedOtpRequest, generateOtpRequest);
    }

    private GenerateOtpResponse generateOtpForChannels(OtpRequest otpRequest, GenerateOtpRequest generateOtpRequest) {
        HashMap<String, Otp> otpHashMap = new HashMap<>();
        StringBuilder otpStringBuilder = new StringBuilder();
        DeliveryChannelEnum deliveryChannel = otpRequest.getDeliveryChannel();

        // Generate OTP for email if applicable
        if (deliveryChannel.equals(DeliveryChannelEnum.OTP_DELIVERY_CHANNEL_BOTH) ||
                deliveryChannel.equals(DeliveryChannelEnum.OTP_DELIVERY_CHANNEL_EMAIL)) {
            Otp savedEmailOtp = createAndSaveOtp(otpRequest, DeliveryChannelEnum.OTP_DELIVERY_CHANNEL_EMAIL, generateOtpRequest.getEmail());
            addOtpToMap(otpHashMap, savedEmailOtp);
            otpStringBuilder.append("Otp Email : ").append(savedEmailOtp.getOtpCode());
        }

        // Generate OTP for mobile if applicable
        if (deliveryChannel.equals(DeliveryChannelEnum.OTP_DELIVERY_CHANNEL_BOTH) ||
                deliveryChannel.equals(DeliveryChannelEnum.OTP_DELIVERY_CHANNEL_MOBILE)) {
            Otp savedMobileOtp = createAndSaveOtp(otpRequest, DeliveryChannelEnum.OTP_DELIVERY_CHANNEL_MOBILE, generateOtpRequest.getMobile());
            addOtpToMap(otpHashMap, savedMobileOtp);

            if (!otpStringBuilder.isEmpty()) {
                otpStringBuilder.append(" - ");
            }
            otpStringBuilder.append("Otp Mobile : ").append(savedMobileOtp.getOtpCode());
        }

        // Save the OTP map back to the request
        otpRequest.setOtpHashMap(otpHashMap);
        otpRequestRepo.save(otpRequest);

        return GenerateOtpResponse.builder()
                .referenceId(otpRequest.getReferenceId())
                .otp(otpStringBuilder.toString())
                .build();
    }

    private Otp createAndSaveOtp(OtpRequest otpRequest, DeliveryChannelEnum deliveryChannel, String contact) {
        Otp otp = Otp.builder()
                .deliveryChannel(deliveryChannel)
                .otpCode(OtpUtil.generateNumericOtp(Constants.NUMERIC_OTP_LENGTH))
                .status(LookUpConstants.STATUS_PENDING)
                .contactInfo(contact)
                .otpRequest(otpRequest)
                .build();

        return otpRepo.saveAndFlush(otp);
    }

    private void addOtpToMap(Map<String, Otp> otpHashMap, Otp otp) {
        otpHashMap.put(otp.getDeliveryChannel() + otp.getOtpCode(), otp);
    }

    @Override
    public ValidateOtpResponse validateOtpAndProcess(ValidateOtpRequest validateOtpRequest) {
        // Find the OTP request by reference ID
        OtpRequest otpRequest = otpRequestRepo.findByReferenceId(validateOtpRequest.getReferenceId())
                .orElseThrow(() -> new NotFoundException("Invalid reference Id"));

        // Get the specific OTP by combining delivery channel and OTP code
        String otpKey = validateOtpRequest.getDeliveryChannel() + validateOtpRequest.getOtp();
        Otp otp = otpRequest.getOtpHashMap().get(otpKey);

        if (otp == null) {
            throw new ServiceException("Invalid Otp");
        }

        // Validate delivery channel compatibility
        validateDeliveryChannelCompatibility(String.valueOf(otpRequest.getDeliveryChannel()), validateOtpRequest.getDeliveryChannel());

        // Check if OTP is expired
        if (OtpUtil.isOtpExpired(otp.getCreatedAt())) {
            otp.setStatus(LookUpConstants.STATUS_EXPIRED);
            otpRepo.save(otp);
            throw new ServiceException("Otp is Expired");
        }

        // Mark OTP as validated and save
        otp.setStatus(LookUpConstants.STATUS_VALIDATED);
        otpRepo.save(otp);

        // Return response (currently returning null in original code)
        return ValidateOtpResponse.builder()
                .validated(Boolean.TRUE)
                .response("OTP Validated Successfully")
                .build();
    }

    private void validateDeliveryChannelCompatibility(String requestDeliveryChannel, String validationDeliveryChannel) {
        // If the original request was for BOTH channels, any validation channel is acceptable
        if (requestDeliveryChannel.equals(LookUpConstants.OTP_DELIVERY_CHANNEL_BOTH)) {
            if (!validationDeliveryChannel.equals(LookUpConstants.OTP_DELIVERY_CHANNEL_EMAIL) &&
                    !validationDeliveryChannel.equals(LookUpConstants.OTP_DELIVERY_CHANNEL_MOBILE)) {
                throw new ServiceException("Invalid Request");
            }
        }
        // If specific channel was requested, validation must match that channel
        else if (!requestDeliveryChannel.equals(validationDeliveryChannel)) {
            throw new ServiceException("Invalid Request");
        }
    }


    @Override
    public RegenerateOtpResponse regenerateOtpAndProcess(RegenerateOtpRequest regenerateOtpRequest) {
        // Find the existing OTP request by reference ID
        OtpRequest otpRequest = otpRequestRepo.findByReferenceId(regenerateOtpRequest.getReferenceId())
                .orElseThrow(() -> new NotFoundException("Invalid reference Id"));

        // Validate if the requested delivery channel is compatible with the original request
        DeliveryChannelEnum requestedChannel = DeliveryChannelEnum.valueOf(regenerateOtpRequest.getDeliveryChannel());
        DeliveryChannelEnum originalDeliveryChannel = otpRequest.getDeliveryChannel();

        if (!isValidDeliveryChannel(originalDeliveryChannel, requestedChannel)) {
            throw new ServiceException("Invalid delivery channel for this OTP request");
        }

        // Mark any existing OTP for this channel as expired
        String existingContact = expireExistingOtps(otpRequest, requestedChannel);

        // Generate new OTP for the requested channel
        Otp newOtp = createAndSaveOtp(otpRequest, requestedChannel, existingContact);

        // Add the new OTP to the hashmap
        Map<String, Otp> otpHashMap = otpRequest.getOtpHashMap();
        if (otpHashMap == null) {
            otpHashMap = new HashMap<>();
        }
        addOtpToMap(otpHashMap, newOtp);

        // Update the OTP request with the new hashmap
        otpRequest.setOtpHashMap(otpHashMap);
        otpRequestRepo.save(otpRequest);

        // Build and return the response
        return RegenerateOtpResponse.builder()
                .referenceId(String.valueOf(otpRequest.getReferenceId()))
                .otp(requestedChannel.equals(LookUpConstants.OTP_DELIVERY_CHANNEL_EMAIL)
                        ? "Otp Email : " + newOtp.getOtpCode()
                        : "Otp Mobile : " + newOtp.getOtpCode())
                .build();
    }

    /**
     * Checks if the requested delivery channel is valid for the original functional area
     */
    private boolean isValidDeliveryChannel(DeliveryChannelEnum originalDeliveryChannel, DeliveryChannelEnum deliveryChannel) {
        // If original request was for BOTH, either email or mobile is valid
        if (originalDeliveryChannel.equals(DeliveryChannelEnum.OTP_DELIVERY_CHANNEL_BOTH)) {
            return deliveryChannel.equals(DeliveryChannelEnum.OTP_DELIVERY_CHANNEL_EMAIL) ||
                    deliveryChannel.equals(DeliveryChannelEnum.OTP_DELIVERY_CHANNEL_MOBILE);
        }
        // Otherwise, the delivery channel must match the original functional area
        return originalDeliveryChannel.equals(deliveryChannel);
    }

    /**
     * Marks existing OTPs for the specified delivery channel as expired
     */
    private String expireExistingOtps(OtpRequest otpRequest, DeliveryChannelEnum deliveryChannel) {
        if (otpRequest.getOtpHashMap() == null) {
            return null;
        }

        List<Otp> otpsToUpdate = new ArrayList<>();

        // Find all OTPs for this delivery channel
        for (Otp otp : otpRequest.getOtpHashMap().values()) {
            if (otp.getDeliveryChannel().equals(deliveryChannel) &&
                    otp.getStatus().equals(LookUpConstants.STATUS_PENDING)) {
                // Mark as expired
                otp.setStatus(LookUpConstants.STATUS_EXPIRED);
                otpsToUpdate.add(otp);
            }
        }

        // Save all expired OTPs in batch if there are any
        if (!otpsToUpdate.isEmpty()) {
            otpRepo.saveAll(otpsToUpdate);
        }
        return otpsToUpdate.getFirst().getContactInfo();
    }

}
