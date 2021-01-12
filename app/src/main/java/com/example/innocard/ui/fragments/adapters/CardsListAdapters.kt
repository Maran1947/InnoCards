package com.example.innocard.ui.fragments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.innocard.R
import com.example.innocard.data.models.Card
import com.example.innocard.ui.fragments.CardsListDirections
import com.example.innocard.utils.Calculations
import kotlinx.android.synthetic.main.fragment_create_card.view.*
import kotlinx.android.synthetic.main.fragment_update_card.view.*
import kotlinx.android.synthetic.main.recycler_card_layout.view.*

class CardsListAdapters: RecyclerView.Adapter<CardsListAdapters.ViewHolder>(){

    var cardlist: List<Card> = emptyList<Card>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
          itemView.findViewById<CardView>(R.id.cv_cardView).setOnClickListener {
              val position: Int = adapterPosition
              val action:NavDirections = CardsListDirections.actionCardsToUpdateCard(cardlist[position])
              itemView.findNavController().navigate(action)
          }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsListAdapters.ViewHolder {
        return ViewHolder (
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_card_layout, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CardsListAdapters.ViewHolder, position: Int) {
        val currentCard: Card = cardlist[position]

        holder.itemView.iv_habit_icon.setImageResource(currentCard.imageId)
        holder.itemView.tv_card_title.text = currentCard.card_title
        holder.itemView.tv_card_desc.text = currentCard.card_description

        holder.itemView.tv_item_bp_1.text = currentCard.card_pt_one
        holder.itemView.tv_item_bp_2.text = currentCard.card_pt_two
        holder.itemView.tv_item_bp_3.text = currentCard.card_pt_three

        holder.itemView.tv_timeElapsed.text = Calculations.calculateTimeBetweenDates(currentCard.card_startTime)
        holder.itemView.tv_item_createdTimeStamp.text = "Since: ${currentCard.card_startTime}"
    }

    override fun getItemCount(): Int {
        return cardlist.size
    }

    fun setData(card: List<Card>){
        this.cardlist = card
        notifyDataSetChanged()
    }
}