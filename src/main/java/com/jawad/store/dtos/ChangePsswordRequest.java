package com.jawad.store.dtos;

import lombok.Data;

@Data
public class ChangePsswordRequest {
    private String oldPassword;
    private String newPassword;
}
