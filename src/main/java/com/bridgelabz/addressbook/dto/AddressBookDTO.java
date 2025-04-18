package com.bridgelabz.addressbook.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddressBookDTO {
    String name;
    String phoneNo;
    List<String> emailAddress;
}
