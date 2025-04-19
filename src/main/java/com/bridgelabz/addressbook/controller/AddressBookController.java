package com.bridgelabz.addressbook.controller;

import com.bridgelabz.addressbook.dto.AddressBookDTO;
import com.bridgelabz.addressbook.dto.ResponseDTO;
import com.bridgelabz.addressbook.service.AddressBookServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("addressbook/")
@Validated
public class AddressBookController {

    @Autowired
    AddressBookServiceImpl addressBookService;

    @PostMapping("createuser")
    public ResponseDTO createUser(@Valid @RequestBody AddressBookDTO dto){
        return addressBookService.addUser(dto);
    }

    @PutMapping("updateuser/{id}")
    public ResponseDTO updateUser(@Valid @RequestBody AddressBookDTO dto, @PathVariable Long id){
        return addressBookService.updateUser(dto,id);
    }

    @DeleteMapping("deleteuser/{id}")
    public ResponseDTO deleteUser(@PathVariable Long id){
        return addressBookService.deleteUser(id);
    }
    @GetMapping("getuser/{id}")
    public ResponseDTO getUserById(@PathVariable Long id){
        return addressBookService.viewUser(id);
    }
}
