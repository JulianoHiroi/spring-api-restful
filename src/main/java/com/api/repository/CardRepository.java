package com.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.model.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {

    public List<Card> findByUserId(UUID userId);

    @Modifying
    @Query(value = "DELETE FROM tb_card WHERE tb_card.id = :id", nativeQuery = true)
    public void deleteCardById(@Param("id") UUID id);

}
