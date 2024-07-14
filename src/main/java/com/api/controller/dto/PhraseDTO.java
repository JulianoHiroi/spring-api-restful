package com.api.controller.dto;

import java.util.UUID;

import com.api.model.Phrase;

public record PhraseDTO(
        UUID id,
        String phrase,
        String phraseTranslated

) {
    public PhraseDTO(Phrase model) {
        this(
                model.getId(),
                model.getPhrase(),
                model.getPhraseTranslated());
    }

    public Phrase toModel() {
        Phrase model = new Phrase();
        model.setId(this.id);
        model.setPhrase(phrase);
        model.setPhraseTranslated(phraseTranslated);
        return model;
    }

}
