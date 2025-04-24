package com.bridgelabz.addressbook.model;

import com.bridgelabz.addressbook.dto.UserDto;
import com.bridgelabz.addressbook.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String userName;
    String password;
    String userEmail;
    String token;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
            @JsonIgnore
    List<AddressBook> userAddressBook;

    public User(UserDto user){
        updateUser(user);
    }

    public User() {

    }

    public void updateUser(UserDto dto){
        this.setPassword( dto.getPassword());
        this.setUserName(dto.getUserName());
        this.setUserEmail(dto.getUserEmail());
        this.setToken(dto.getToken());
        this.setUserAddressBook(new ArrayList<>());
    }

}
