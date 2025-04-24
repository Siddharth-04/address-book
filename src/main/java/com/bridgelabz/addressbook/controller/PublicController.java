package com.bridgelabz.addressbook.controller;

import com.bridgelabz.addressbook.dto.ForgetPasswordDto;
import com.bridgelabz.addressbook.dto.ResetPasswordDto;
import com.bridgelabz.addressbook.dto.ResponseDTO;
import com.bridgelabz.addressbook.dto.UserDto;
import com.bridgelabz.addressbook.model.User;
import com.bridgelabz.addressbook.repository.UserRepository;
import com.bridgelabz.addressbook.service.EmailService;
import com.bridgelabz.addressbook.service.UserServiceImpl;
import com.bridgelabz.addressbook.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    private static final PasswordEncoder passEncoder = new BCryptPasswordEncoder();

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

    @PostMapping("forget-pass")
    public ResponseDTO forgetPass(@RequestBody ForgetPasswordDto dto){
        User user = userRepository.findByUserEmail(dto.getUserEmail());
        if(user == null){
            return new ResponseDTO("User not found",HttpStatus.BAD_REQUEST);
        }
        int generatedOtp = (int)(Math.random()*8999)+1000;
        String otp = Integer.toString(generatedOtp);
        user.setToken(otp);
        userRepository.save(user);
        String email = user.getUserEmail();
        emailService.sendEmail(email,"Change your password","Dear User, as per your request you can now change your password, your OTP is : "+otp);
        return new ResponseDTO("OTP sent successfully to registered email",HttpStatus.OK);
    }

    @PostMapping("reset-pass")
    public ResponseDTO changePassByToken(@RequestBody ResetPasswordDto dto){
        User user = userRepository.findByUserEmail(dto.getUserEmail());
        if(!dto.getToken().equals(user.getToken())){
            return new ResponseDTO("Token is not valid",HttpStatus.UNAUTHORIZED);
        }

        //logic
        user.setPassword(passEncoder.encode(dto.getPassword()));
        System.out.println(user.getPassword());
        user.setToken(null);
        userRepository.save(user);

        return new ResponseDTO("User changed its pass successfully",HttpStatus.OK);
    }

}
