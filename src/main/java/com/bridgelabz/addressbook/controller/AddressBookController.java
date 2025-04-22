package com.bridgelabz.addressbook.controller;

import com.bridgelabz.addressbook.dto.AddressBookDTO;
import com.bridgelabz.addressbook.dto.ResponseDTO;
import com.bridgelabz.addressbook.model.AddressBook;
import com.bridgelabz.addressbook.model.User;
import com.bridgelabz.addressbook.repository.UserRepository;
import com.bridgelabz.addressbook.service.AddressBookServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("addressbook/")
@Validated
public class AddressBookController {

    @Autowired
    AddressBookServiceImpl addressBookService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("createbook")
    public ResponseDTO createBook(@Valid @RequestBody AddressBookDTO dto){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User user = userRepository.findByUserName(userName);

            if(user != null){
                AddressBook addressBook = new AddressBook(dto);
                addressBook.setUser(user);
                return addressBookService.addBook(addressBook);
            }else {
                return new ResponseDTO("User not found", HttpStatus.NOT_FOUND);
            }

        }catch (Exception e){
            System.out.println(e);
            return new ResponseDTO("Error creating addressBook", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("updatebook/{id}")
    public ResponseDTO updateBook(@Valid @RequestBody AddressBookDTO dto, @PathVariable Long id){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUserName(username);
            Long userId = user.getId();
            return addressBookService.updateAddressBook(dto,id,userId);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseDTO("Error updating addressBook", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("deletebook/{id}")
    public ResponseDTO deleteUser(@PathVariable Long id){

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUserName(username);
            Long userId = user.getId();
            return addressBookService.deleteBook(id,userId);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseDTO("Error deleting addressBook", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("getbook/{id}")
    public ResponseDTO getBookById(@PathVariable Long id){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUserName(username);
            Long userId = user.getId();
            return addressBookService.viewBook(id,userId);
        } catch (Exception e) {
            return new ResponseDTO("Error while fetching addressBook",HttpStatus.BAD_REQUEST);
        }

    }
}
