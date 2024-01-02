package com.bookmyshow.api.services;

import com.bookmyshow.api.models.User;
import com.bookmyshow.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@Qualifier("apple")
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String email) {
        User user = new User();
        user.setEmail(email);

        User savedUser = userRepository.save(user);

        return savedUser;
    }
}
