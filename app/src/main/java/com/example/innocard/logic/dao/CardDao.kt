package com.example.innocard.logic.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.innocard.data.models.Card

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCard(card: Card)

    @Update
    suspend fun updateCard(card: Card)

    @Delete
    suspend fun deleteCard(card: Card)

    @Query("SELECT * FROM card_table ORDER BY id DESC")
    fun getAllCards(): LiveData<List<Card>>

    @Query("DELETE FROM card_table")
    suspend fun deleteAll()
}