package com.example.innocard.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "card_table")
data class Card(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val card_title: String,
    val card_description: String,
    val card_pt_one: String,
    val card_pt_two: String,
    val card_pt_three: String,
    val card_startTime: String,
    val imageId: Int
) : Parcelable

