package com.bridgelabz.addressbook.dto;

import lombok.Data;

@Data
public class ResetPasswordDto {
    String userEmail;
    String token;
    String password;
}
