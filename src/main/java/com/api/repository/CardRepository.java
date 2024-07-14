package com.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.model.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {

    public List<Card> findByUserId(UUID userId);

}
