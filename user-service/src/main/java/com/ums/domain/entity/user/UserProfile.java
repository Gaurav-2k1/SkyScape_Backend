package com.ums.domain.entity.user;

import com.ssh.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserProfile extends BaseEntity {
    private String firstName;
    private String lastName;
    private String emailId;
    private String mobileNumber;
}
