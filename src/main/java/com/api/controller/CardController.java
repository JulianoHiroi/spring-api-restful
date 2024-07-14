package com.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.controller.dto.CardDTO;
import com.api.model.Card;
import com.api.service.CardService;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable("id") String id) {
        var card = cardService.getCardById(id);
        return ResponseEntity.ok(new CardDTO(card));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CardDTO>> getAllCardsByUser(@PathVariable("userId") String userId) {
        var cards = cardService.getAllCardsByUser(userId);
        var cardsDto = cards.stream().map(CardDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(cardsDto);
    }

    @PostMapping
    public ResponseEntity<CardDTO> createCard(@RequestBody Card card) {
        return ResponseEntity.ok(new CardDTO(cardService.createCard(card)));
    }

    @PostMapping("/suffle/{userId}")
    public ResponseEntity<List<CardDTO>> getSuffleCards(@PathVariable("userId") String userId) {
        var cards = cardService.getSuffleCards(userId);
        var cardsDto = cards.stream().map(CardDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(cardsDto);
    }

    @PatchMapping
    public ResponseEntity<CardDTO> updateCard(@RequestBody Card card) {
        return ResponseEntity.ok(new CardDTO(cardService.updateCard(card)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable("id") String id) {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }

}
