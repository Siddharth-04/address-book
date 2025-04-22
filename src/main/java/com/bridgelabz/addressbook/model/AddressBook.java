package com.bridgelabz.addressbook.model;

import com.bridgelabz.addressbook.dto.AddressBookDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class AddressBook {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    String name;
    String phoneNo;

    @ElementCollection
    @CollectionTable(name = "user_emails",joinColumns = @JoinColumn(name = "id"))
    @Column(name = "email")
    private List<String> emailAdress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public AddressBook() {

    }


    public void updateAddressBook(AddressBookDTO dto){
        this.name = dto.getName();
        this.phoneNo = dto.getPhoneNo();
        this.emailAdress = new ArrayList<>(dto.getEmailAddress());
    }

    public AddressBook(AddressBookDTO dto) {
        updateAddressBook(dto);
    }
}
