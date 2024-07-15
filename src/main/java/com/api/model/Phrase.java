package com.api.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "tb_phrase")
public class Phrase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private UUID id;

    private String phrase;

    private String phraseTranslated;

    public Phrase() {
    }

    public Phrase(String phrase, String phraseTranslated) {
        this.phrase = phrase;
        this.phraseTranslated = phraseTranslated;
    }

    public String getPhraseTranslated() {
        return phraseTranslated;
    }

    public void setPhraseTranslated(String phraseTranslated) {
        this.phraseTranslated = phraseTranslated;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

}
