package com.bridgelabz.addressbook.repository;

import com.bridgelabz.addressbook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserName(String userName);
    User findByUserEmail(String userEmail);
}
