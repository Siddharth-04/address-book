package com.bridgelabz.addressbook.service;

import com.bridgelabz.addressbook.dto.AddressBookDTO;
import com.bridgelabz.addressbook.dto.ResponseDTO;
import com.bridgelabz.addressbook.model.AddressBook;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public interface IAddressBookService {
    ResponseDTO addBook(@RequestBody AddressBook addressBook);
    ResponseDTO deleteBook(@PathVariable Long id,Long userId);
    ResponseDTO viewBook(@PathVariable Long id,Long userId);
    ResponseDTO updateAddressBook(@RequestBody AddressBookDTO addressBookDTO,@PathVariable Long id,Long userId);
}
