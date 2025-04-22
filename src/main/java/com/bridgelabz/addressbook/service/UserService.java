package com.bridgelabz.addressbook.service;

import com.bridgelabz.addressbook.dto.ResponseDTO;
import com.bridgelabz.addressbook.dto.UserDto;

public interface UserService {
    public ResponseDTO saveUser(UserDto user);
    public ResponseDTO findById(Long id);
    public ResponseDTO deleteById(Long id);
}