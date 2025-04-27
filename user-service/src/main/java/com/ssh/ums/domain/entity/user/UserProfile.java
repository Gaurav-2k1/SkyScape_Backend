package com.ssh.ums.domain.entity.user;

import com.ssh.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserProfile extends BaseEntity {
    private String firstName;
    @Builder.Default
    private UUID userId = UUID.randomUUID();
    private String lastName;
    private String emailId;
    private String mobileNumber;
}
