package com.bridgelabz.addressbook.service;

import com.bridgelabz.addressbook.dto.AddressBookDTO;
import com.bridgelabz.addressbook.dto.ResponseDTO;
import com.bridgelabz.addressbook.model.AddressBook;
import com.bridgelabz.addressbook.repository.AddressBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class AddressBookServiceImpl implements IAddressBookService{
    @Autowired
    AddressBookRepository addressBookRepository;

    @Autowired
    EmailService emailService;

    @Override
    public ResponseDTO addBook(AddressBook addressBook) {
        addressBookRepository.save(addressBook);
        List<String> emailAdress = addressBook.getEmailAdress();

        for(String s : emailAdress){
            emailService.sendEmail(s,"Welcome to AddressBook App","Dear User,thanks for using our app !");
        }

        log.info("User created successfully !");
        return new ResponseDTO("User created successfully",addressBook);
    }

    @Override
    public ResponseDTO deleteBook(Long id,Long userId) {
        Optional<AddressBook> obj = addressBookRepository.findById(id);
        if(obj.isPresent()){
            AddressBook addressbook = obj.get();

            if(!addressbook.getUser().getId().equals(userId)){
                return new ResponseDTO("User not authenticated !", HttpStatus.UNAUTHORIZED);
            }
            addressBookRepository.deleteById(id);
            return new ResponseDTO("AddressBook deleted successfully !", HttpStatus.OK);
        }

        return new ResponseDTO("AddressBook not found with this id :"+id,HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseDTO viewBook(Long id,Long userId) {
        Optional<AddressBook> obj = addressBookRepository.findById(id);
        if(obj.isPresent()){
            AddressBook addressbook = obj.get();
            if(!addressbook.getUser().getId().equals(userId)){
                return new ResponseDTO("User not authenticated",HttpStatus.UNAUTHORIZED);
            }
            return new ResponseDTO("AddressBook fetched successfully !", addressbook);
        }

        return new ResponseDTO("User not found with this id :"+id,HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseDTO updateAddressBook(AddressBookDTO addressBookDTO, Long id, Long userId) {
        Optional<AddressBook> obj = addressBookRepository.findById(id);

        if(obj.isPresent()){
            AddressBook addressbook = obj.get();

            if(!addressbook.getUser().getId().equals(userId)){
                return new ResponseDTO("User not authenticated !", HttpStatus.UNAUTHORIZED);
            }
            if(addressBookDTO.getName() != null){
                addressbook.setName(addressBookDTO.getName());
            }
            if(addressBookDTO.getEmailAddress() != null){
                addressbook.setEmailAdress(addressBookDTO.getEmailAddress());
            }
            if(addressBookDTO.getPhoneNo() != null){
                addressbook.setPhoneNo(addressBookDTO.getPhoneNo());
            }

            addressBookRepository.save(addressbook);
            return new ResponseDTO("AddressBook updated successfully !", addressbook);
        }

        return new ResponseDTO("Error updating addressBook !", HttpStatus.NOT_FOUND);
    }
}
