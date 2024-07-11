package com.api.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    public User findByEmail(String email);
}