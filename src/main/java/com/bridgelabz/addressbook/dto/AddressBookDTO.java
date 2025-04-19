package com.bridgelabz.addressbook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class AddressBookDTO {

    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$",message = "User name invalid !")
    @NotBlank
    String name;

    @Pattern(regexp = "^[6-9]{1}[0-9]{9}",message = "Must have 10 digits")
    @NotBlank
    String phoneNo;

    List<@Pattern(regexp = "^[a-zA-Z]+[\\w.+]*@[A-Za-z]+(?:\\.[a-zA-Z0-9-]+)*[.][a-zA-Z]{2,}$",message = "User email invalid")
            String> emailAddress;
}
