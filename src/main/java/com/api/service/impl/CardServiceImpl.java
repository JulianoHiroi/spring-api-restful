package com.api.service.impl;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

import com.api.model.Card;
import com.api.model.User;
import com.api.repository.CardRepository;
import com.api.repository.UserRepository;
import com.api.service.CardService;
import com.api.service.DictionaryService;
import com.api.service.TranslateService;
import com.api.service.dto.DictionaryDTO;
import com.api.service.exception.NotFoundException;
import com.api.service.exception.WordNotFound;

import com.api.model.Phrase;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardServiceImpl extends CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    private final DictionaryService dictionaryService;
    private final TranslateService translateService;

    public CardServiceImpl(CardRepository cardRepository, UserRepository userRepository,
            DictionaryService dictionaryService, TranslateService translateService) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.dictionaryService = dictionaryService;
        this.translateService = translateService;
    }

    public Card createCard(Card card) {
        User userExist = this.userRepository.findById(card.getUser().getId()).orElse(null);
        if (userExist == null) {
            throw new IllegalArgumentException("User not found");
        }

        List<Card> CardUsers = this.cardRepository.findByUserId(card.getUser().getId());
        CardUsers.forEach(cardUser -> {
            if (cardUser.getWord().equalsIgnoreCase(card.getWord())) {
                throw new IllegalArgumentException("Card already exists");
            }
        });
        card.setWord(card.getWord().toLowerCase());
        DictionaryDTO data = dictionaryService.searchWord(card.getWord());
        if (data == null)
            throw new WordNotFound();
        String translatedWord = translateService.translateToPortugues(card.getWord());
        List<Phrase> translatedPhrases = data.getExamples().stream().limit(2).map(phrase -> {
            return new Phrase(phrase, translateService.translateToPortugues(phrase));
        }).toList();
        card.setWordTranslated(translatedWord);
        card.setPhrases(translatedPhrases);
        card.setPriority(10);
        card.setLanguage("en");
        return cardRepository.save(card);
    }

    @Transactional(readOnly = true)
    public Card getCardById(String id) {
        var card = cardRepository.findById(UUID.fromString(id)).orElse(null);
        if (card == null) {
            throw new NotFoundException();
        }
        return card;
    }

    @Transactional(readOnly = true)
    public List<Card> getAllCardsByUser(String userId) {
        return cardRepository.findByUserId(UUID.fromString(userId));
    }

    @Transactional
    public Card updateCard(Card card) {
        Card cardExist = cardRepository.findById(card.getId()).orElse(null);
        if (cardExist == null) {
            throw new IllegalArgumentException("Card not found");
        }
        if (card.getWord() != null) {
            List<Card> cards = cardRepository.findByUserId(cardExist.getUser().getId());
            cards.forEach(cardUser -> {
                if (cardUser.getWord().equals(card.getWord()) && !cardUser.getId().equals(card.getId())) {
                    throw new IllegalArgumentException("Card already exists");
                }
            });
            cardExist.setWord(card.getWord());
        }
        if (card.getWordTranslated() != null)
            cardExist.setWordTranslated(card.getWordTranslated());
        if (card.getPhrases() != null)
            cardExist.setPhrases(card.getPhrases());
        if (card.getLanguage() != null)
            cardExist.setLanguage(card.getLanguage());
        if (card.getPriority() != 0)
            cardExist.setPriority(card.getPriority());
        return cardRepository.save(cardExist);
    }

    @Transactional
    public void deleteCard(String id) {
        var cardExist = cardRepository.findById(UUID.fromString(id)).orElse(null);
        if (cardExist == null) {
            throw new NotFoundException();
        }
        cardRepository.deleteCardById(UUID.fromString(id));
    }

    @Transactional(readOnly = true)
    public List<Card> getSuffleCards(String userId) {
        return null;
    }

}
