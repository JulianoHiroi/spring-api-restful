package com.api.service;

import java.util.List;

import com.api.model.Card;

public abstract class CardService {
    public abstract Card createCard(Card card);

    public abstract List<Card> getAllCardsByUser(String userId);

    public abstract Card getCardById(String id);

    public abstract Card updateCard(Card card);

    public abstract void deleteCard(String id);

    public abstract List<Card> getSuffleCards(String userId);

}
