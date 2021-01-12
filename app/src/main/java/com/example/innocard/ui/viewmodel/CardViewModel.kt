package com.example.innocard.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.innocard.data.database.CardDatabase
import com.example.innocard.data.models.Card
import com.example.innocard.logic.repository.CardRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CardRepository
    val getAllHabits: LiveData<List<Card>>

    init {
        val habitDao= CardDatabase.getDatabase(application).cardDao()
        repository = CardRepository(habitDao)

        getAllHabits = repository.getAllCards
    }

    fun addCard(card: Card) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCard(card)
        }
    }

    fun updateCard(card: Card) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCard(card)
        }
    }

    fun deleteCard(card: Card) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCard(card)
        }
    }

    fun deleteAllCards() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllCards()
        }
    }


}