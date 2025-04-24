package com.bridgelabz.addressbook.service;

import com.bridgelabz.addressbook.dto.ChangePasswordDto;
import com.bridgelabz.addressbook.dto.ResponseDTO;
import com.bridgelabz.addressbook.dto.UserDto;
import com.bridgelabz.addressbook.model.User;
import com.bridgelabz.addressbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final PasswordEncoder passEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;



    public ResponseDTO saveUser(UserDto user){
        user.setPassword(passEncoder.encode(user.getPassword()));
        User newUser = new User(user);
        userRepository.save(newUser);

        return new ResponseDTO("User created Successfully",user);
    }

    public ResponseDTO findById(Long id){
        Optional<User> obj = userRepository.findById(id);

        if(obj.isPresent()){
            User user = obj.get();
            return new ResponseDTO("User fetched successfully",user);
        }

        return new ResponseDTO("User not found", HttpStatus.NOT_FOUND);
    }

    public ResponseDTO deleteById(Long id){
       Optional<User> obj = userRepository.findById(id);

        if(obj.isPresent()){
            userRepository.deleteById(id);
            return new ResponseDTO("User deleted successfully",HttpStatus.NO_CONTENT);
        }

        return new ResponseDTO("User not found", HttpStatus.NOT_FOUND);
    }

    public ResponseDTO changePassword(ChangePasswordDto user, User exiistingUser){
        String password = passEncoder.encode(user.getPassword());
        exiistingUser.setPassword(password);
        userRepository.save(exiistingUser);
        return new ResponseDTO("Password successfully reset !",exiistingUser);
    }

}
