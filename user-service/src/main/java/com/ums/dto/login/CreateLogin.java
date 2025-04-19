package com.ums.dto.login;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Builder
@Data
public class CreateLogin {
    private UUID twoReferenceId;
    private String authMethod;

}
