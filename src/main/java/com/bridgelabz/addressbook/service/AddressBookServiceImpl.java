package com.bridgelabz.addressbook.service;

import com.bridgelabz.addressbook.dto.AddressBookDTO;
import com.bridgelabz.addressbook.dto.ResponseDTO;
import com.bridgelabz.addressbook.model.AddressBook;
import com.bridgelabz.addressbook.repository.AddressBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AddressBookServiceImpl implements IAddressBookService{
    @Autowired
    AddressBookRepository addressBookRepository;

    @Override
    public ResponseDTO addUser(AddressBookDTO addressBookDTO) {
        AddressBook addressBook = new AddressBook(addressBookDTO);
        addressBookRepository.save(addressBook);
        log.info("User created successfully !");
        return new ResponseDTO("User created successfully",addressBookDTO);
    }

    @Override
    public ResponseDTO deleteUser(Long id) {
        Optional<AddressBook> obj = addressBookRepository.findById(id);
        if(obj.isPresent()){
            addressBookRepository.deleteById(id);
            return new ResponseDTO("User deleted successfully !", HttpStatus.OK);
        }

        return new ResponseDTO("User not found with this id :"+id,HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseDTO viewUser(Long id) {
        Optional<AddressBook> obj = addressBookRepository.findById(id);
        if(obj.isPresent()){
            AddressBook user = obj.get();
            return new ResponseDTO("User deleted successfully !", user);
        }

        return new ResponseDTO("User not found with this id :"+id,HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseDTO updateUser(AddressBookDTO addressBookDTO, Long id) {
        Optional<AddressBook> obj = addressBookRepository.findById(id);
        if(obj.isPresent()){
            AddressBook user = obj.get();
            if(addressBookDTO.getName() != null){
                user.setName(addressBookDTO.getName());
            }
            if(addressBookDTO.getEmailAddress() != null){
                user.setEmailAdress(addressBookDTO.getEmailAddress());
            }
            if(addressBookDTO.getPhoneNo() != null){
                user.setPhoneNo(addressBookDTO.getPhoneNo());
            }

            addressBookRepository.save(user);
            return new ResponseDTO("User updated successfully !", user);
        }

        return new ResponseDTO("Error updating user !", HttpStatus.NOT_FOUND);
    }
}
