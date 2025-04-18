package com.bridgelabz.addressbook.service;

import com.bridgelabz.addressbook.dto.AddressBookDTO;
import com.bridgelabz.addressbook.dto.ResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public interface IAddressBookService {
    ResponseDTO addUser(@RequestBody AddressBookDTO addressBookDTO);
    ResponseDTO deleteUser(@PathVariable Long id);
    ResponseDTO viewUser(@PathVariable Long id);
    ResponseDTO updateUser(@RequestBody AddressBookDTO addressBookDTO,@PathVariable Long id);
}
