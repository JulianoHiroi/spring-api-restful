package com.api.service.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.api.model.Card;
import com.api.model.User;
import com.api.repository.CardRepository;
import com.api.repository.UserRepository;
import com.api.service.CardService;
import com.api.service.exception.NotFoundException;

import org.springframework.transaction.annotation.Transactional;

@Service
public class CardServiceImpl extends CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);

    public CardServiceImpl(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    public Card createCard(Card card) {
        User userExist = this.userRepository.findById(card.getUser().getId()).orElse(null);
        if (userExist == null) {
            throw new IllegalArgumentException("User not found");
        }

        List<Card> CardUsers = this.cardRepository.findByUserId(card.getUser().getId());
        CardUsers.forEach(cardUser -> {
            if (cardUser.getWord().equals(card.getWord())) {
                throw new IllegalArgumentException("Card already exists");
            }
        });
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
        logger.info("Card exist: " + cardExist);
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
        logger.info("Card exist: " + cardExist);
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
