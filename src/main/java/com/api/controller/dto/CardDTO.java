package com.api.controller.dto;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.api.model.Card;

public record CardDTO(
        UUID id,
        String word,
        String wordTranslated,
        String language,
        List<PhraseDTO> phrases,
        int priority) {

    public CardDTO(Card model) {
        this(
                model.getId(),
                model.getWord(),
                model.getWordTranslated(),
                model.getLanguage(),
                ofNullable(model.getPhrases()).orElse(emptyList()).stream().map(PhraseDTO::new)
                        .collect(Collectors.toList()),
                model.getPriority());
    }

    public Card toModel() {
        Card model = new Card();
        model.setId(this.id);
        model.setWord(word);
        ;
        model.setWordTranslated(wordTranslated);
        model.setLanguage(language);
        model.setPhrases(ofNullable(this.phrases).orElse(emptyList()).stream().map(PhraseDTO::toModel)
                .collect(Collectors.toList()));
        model.setPriority(priority);
        return model;
    }
}
