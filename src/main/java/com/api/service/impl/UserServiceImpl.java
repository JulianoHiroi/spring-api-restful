package com.api.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.api.model.User;
import com.api.repository.UserRepository;
import com.api.service.UserService;

@Service
public class UserServiceImpl extends UserService {

    private final UserRepository userRepository;

    private UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        var userExists = userRepository.findByEmail(user.getEmail());
        if (userExists != null) {
            throw new IllegalArgumentException("User already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        // convertendo o id para UUID
        return userRepository.findById(UUID.fromString(id)).orElse(null);
    }

    @Override
    public User updateUser(User user) {
        Optional<User> userExists = userRepository.findById(user.getId());
        if (userExists.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        if (user.getEmail() != userExists.get().getEmail()) {
            var userExistsByEmail = userRepository.findByEmail(user.getEmail());
            if (userExistsByEmail != null) {
                throw new IllegalArgumentException("Email already exists");
            }
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(UUID.fromString(id));
    }

}
