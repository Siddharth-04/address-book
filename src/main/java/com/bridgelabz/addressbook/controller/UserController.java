package com.bridgelabz.addressbook.controller;

import com.bridgelabz.addressbook.dto.ResetPasswordDto;
import com.bridgelabz.addressbook.dto.ResponseDTO;
import com.bridgelabz.addressbook.dto.UserDto;

import com.bridgelabz.addressbook.model.User;
import com.bridgelabz.addressbook.repository.UserRepository;
import com.bridgelabz.addressbook.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;

    @PutMapping("reset-password")
    public ResponseDTO resetPassword(@RequestBody ResetPasswordDto user){
//        try{
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
//            UserDetails userDetails  = userDetailsService.loadUserByUsername(user.getUserName());
//
//            if(userDetails!= null && user.getUserName().equals(userDetails.getUsername())){
//                return userService.resetPassword(user);
//            }
//            return new ResponseDTO("User not authenticated !", HttpStatus.UNAUTHORIZED);
//
//        }catch(Exception e){
//            System.out.println(e);
//            return new ResponseDTO("Error resetting password",HttpStatus.BAD_REQUEST);
//        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User existingUser = userRepository.findByUserName(username);

        if(existingUser ==null){
            return new ResponseDTO("User not found",HttpStatus.NOT_FOUND);
        }

        return userService.resetPassword(user,existingUser);
    }
}
