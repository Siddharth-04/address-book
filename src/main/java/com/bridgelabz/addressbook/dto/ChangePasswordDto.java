package com.bridgelabz.addressbook.dto;

import lombok.Data;

@Data
public class ChangePasswordDto {
    String oldPassword;
    String password;
}
