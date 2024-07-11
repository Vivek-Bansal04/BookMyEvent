package com.bookmyshow.api.services;

import com.bookmyshow.api.dtos.UserRequestDTO;
import com.bookmyshow.api.models.User;
import com.bookmyshow.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserRequestDTO request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());

        return userRepository.save(user);
    }
}
