package com.ocean.springproject.service.impl;

import com.ocean.springproject.domain.User;
import com.ocean.springproject.repository.UserRepository;
import com.ocean.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByEmail(String email) {

        Optional<User> user = this.userRepository.findByEmail(email);

        if (user.isPresent()) {
            return user.get();
        }

        return null;
    }

    @Override
        public Page<User> findAll(Pageable pageable) {

        Page<User> pageUsers = this.userRepository.findAll(pageable);

        if (pageUsers.getSize() > 0) {
            return pageUsers;
        }

        return null;
    }
}
