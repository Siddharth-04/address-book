package com.bridgelabz.addressbook.dto;

import com.bridgelabz.addressbook.model.AddressBook;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    String userName;
    String password;
    String userEmail;
    String token;
    List<AddressBook> userAddressBook;
}
