package com.api.controller.dto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.api.model.User;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

public record UserDTO(
        UUID id,
        String name,
        String email,
        List<CardDTO> cards) {

    public UserDTO(User model) {
        this(
                model.getId(),
                model.getName(),
                model.getEmail(),
                ofNullable(model.getCards()).orElse(emptyList()).stream().map(CardDTO::new)
                        .collect(Collectors.toList()));
    }

    public User toModel() {
        User model = new User();
        model.setId(this.id);
        model.setName(name);
        model.setEmail(email);
        model.setCards(ofNullable(this.cards).orElse(emptyList()).stream().map(CardDTO::toModel)
                .collect(Collectors.toList()));
        return model;
    }

}
