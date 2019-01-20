package com.ocean.springproject.service;

import com.ocean.springproject.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User findByEmail(String email);

    Page<User> findAll(Pageable pageable);
}
