package com.api.service;

import java.util.List;

import com.api.model.User;

public abstract class UserService {
    public abstract User createUser(User user);

    public abstract List<User> getAllUsers();

    public abstract User getUserById(String id);

    public abstract User updateUser(User user);

    public abstract void deleteUser(String id);
}
