package com.api.service.impl;

import static java.util.Optional.ofNullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import com.api.model.User;
import com.api.repository.UserRepository;
import com.api.service.UserService;
import com.api.service.exception.BusinessException;
import com.api.service.exception.NotFoundException;

@Service
public class UserServiceImpl extends UserService {

    private final UserRepository userRepository;

    private UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        ofNullable(user).orElseThrow(() -> new BusinessException("User to create must not be null."));

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
        var user = userRepository.findById(UUID.fromString(id)).orElse(null);
        if (user == null) {
            throw new NotFoundException();
        }
        return user;
    }

    @Override
    public User updateUser(User user) {
        ofNullable(user).orElseThrow(() -> new BusinessException("User to update must not be null."));
        Optional<User> userExists = userRepository.findById(user.getId());
        if (userExists.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        if (user.getEmail() != userExists.get().getEmail()) {
            var userExistsByEmail = userRepository.findByEmail(user.getEmail());
            if (userExistsByEmail != null) {
                throw new IllegalArgumentException("Email already used");
            }
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        var userExist = userRepository.findById(UUID.fromString(id)).orElse(null);
        if (userExist == null) {
            throw new NotFoundException();
        }
        userRepository.deleteById(UUID.fromString(id));
    }

}
