package com.example.innocard.ui.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.innocard.R
import com.example.innocard.data.models.Card
import com.example.innocard.ui.viewmodel.CardViewModel
import com.example.innocard.utils.Calculations
import kotlinx.android.synthetic.main.fragment_update_card.*
import java.util.*

class UpdateCard : Fragment(R.layout.fragment_update_card),
    TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private var title = ""
    private var description = ""
    private var pt_one = ""
    private var pt_two = ""
    private var pt_three = ""
    private var drawableSelected = 0
    private var timeStamp = ""

    private lateinit var cardViewModel: CardViewModel

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var cleanDate = ""
    private var cleanTime = ""

    private val args by navArgs<UpdateCardArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        cardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)

        //Retrieve data from our habit list
        et_cardTitle_update.setText(args.selectedCard.card_title)
        et_cardDescription_update.setText(args.selectedCard.card_description)
        et_point1_update.setText(args.selectedCard.card_pt_one)
        et_point2_update.setText(args.selectedCard.card_pt_two)
        et_point3_update.setText(args.selectedCard.card_pt_three)

        //Pick a drawable
        drawableSelected()

        //Pick the date and time again
        pickDateAndTime()

        //Confirm changes and update the selected item
        btn_confirm_update.setOnClickListener {
            updateCard()
        }

        //Show the options menu in this fragment
        setHasOptionsMenu(true)

    }

    private fun drawableSelected() {
        img_1_update.setOnClickListener {
            img_1_update.isSelected = !img_1_update.isSelected
            drawableSelected = R.drawable.img_1
            img_1_update.alpha = 1.0F
            img_2_update.alpha = 0.3F
            img_3_update.alpha = 0.3F

            //de-select the other options when we pick an image
            img_2_update.isSelected = false
            img_3_update.isSelected = false
        }

        img_2_update.setOnClickListener {
            img_2_update.isSelected = !img_2_update.isSelected
            drawableSelected = R.drawable.img_2
            img_1_update.alpha = 0.3F
            img_2_update.alpha = 1.0F
            img_3_update.alpha = 0.3F

            //de-select the other options when we pick an image
            img_1_update.isSelected = false
            img_3_update.isSelected = false
        }

        img_3_update.setOnClickListener {
            img_3_update.isSelected = !img_3_update.isSelected
            drawableSelected = R.drawable.img_3
            img_1_update.alpha = 0.3F
            img_2_update.alpha = 0.3F
            img_3_update.alpha = 1.0F

            //de-select the other options when we pick an image
            img_1_update.isSelected = false
            img_2_update.isSelected = false
        }
    }

    private fun updateCard() {
        //Get text from editTexts
        title = et_cardTitle_update.text.toString()
        description = et_cardDescription_update.text.toString()

        //Create a timestamp string for our recyclerview
        timeStamp = "$cleanDate $cleanTime"

        //Check that the form is complete before submitting data to the database
        if (!(title.isEmpty() || description.isEmpty() || timeStamp.isEmpty() || drawableSelected == 0)) {
            val card =
                Card(args.selectedCard.id, title, description, pt_one, pt_two, pt_three, timeStamp, drawableSelected)

            //add the habit if all the fields are filled
            cardViewModel.updateCard(card)
            Toast.makeText(context, "Card updated! successfully!", Toast.LENGTH_SHORT).show()

            //navigate back to our home fragment
            findNavController().navigate(R.id.action_updateCard_to_cards)
        } else {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }
    //Handle date and time picking
    //set on click listeners for our data and time pickers
    private fun pickDateAndTime() {
        btn_pickDate_update.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        btn_pickTime_update.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(context, this, hour, minute, true).show()
        }

    }

    private fun getTimeCalendar() {
        val cal = Calendar.getInstance()
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun getDateCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        cleanTime = Calculations.cleanTime(p1, p2)
        tv_timeSelected_update.text = "Time: $cleanTime"
    }

    @SuppressLint("SetTextI18n")
    override fun onDateSet(p0: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {
        cleanDate = Calculations.cleanDate(dayX, monthX, yearX)
        tv_dateSelected_update.text = "Date: $cleanDate"
    }
    //------------------------------------------

    //Create options menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.single_card, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_del -> {
                deleteCard(args.selectedCard)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //------------------------------------------

    //Delete a single Habit
    private fun deleteCard(card: Card) {
        cardViewModel.deleteCard(card)
        Toast.makeText(context, "Card successfully deleted!", Toast.LENGTH_SHORT).show()

        findNavController().navigate(R.id.action_updateCard_to_cards)
    }
    //------------------------------------------
}