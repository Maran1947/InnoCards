package com.example.innocard.logic.repository

import androidx.lifecycle.LiveData
import com.example.innocard.data.models.Card
import com.example.innocard.logic.dao.CardDao

class CardRepository (private val cardDao: CardDao) {
    val getAllCards: LiveData<List<Card>> = cardDao.getAllCards()

    suspend fun addCard(card: Card) {
        cardDao.addCard(card)
    }

    suspend fun updateCard(card: Card) {
        cardDao.updateCard(card)
    }

    suspend fun deleteCard(card: Card) {
        cardDao.deleteCard(card)
    }

    suspend fun deleteAllCards() {
        cardDao.deleteAll()
    }
}