package com.olabode.intuit.docmanager.repository;

import com.olabode.intuit.docmanager.domain.security.User;


import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}