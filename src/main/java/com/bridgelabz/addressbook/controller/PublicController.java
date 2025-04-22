package com.bridgelabz.addressbook.controller;

import com.bridgelabz.addressbook.dto.ResponseDTO;
import com.bridgelabz.addressbook.dto.UserDto;
import com.bridgelabz.addressbook.service.UserServiceImpl;
import com.bridgelabz.addressbook.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public/")
public class PublicController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("signup")
    public ResponseDTO saveUser(@RequestBody UserDto user){
        return userService.saveUser(user);
    }


    @GetMapping("login")
    public ResponseDTO login(@RequestBody UserDto user){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
            UserDetails userDetails  = userDetailsService.loadUserByUsername(user.getUserName());
            String jwtToken = jwtUtils.generateToken(userDetails.getUsername());
            return new ResponseDTO(jwtToken, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseDTO("Incorrect username or password",HttpStatus.BAD_REQUEST);
        }
    }

}
