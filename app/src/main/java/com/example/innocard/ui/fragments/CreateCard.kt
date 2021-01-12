package com.example.innocard.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.innocard.R
import com.example.innocard.data.models.Card
import com.example.innocard.ui.viewmodel.CardViewModel
import com.example.innocard.utils.Calculations
import java.util.*

class CreateCard : Fragment(R.layout.fragment_create_card),
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

        private lateinit var btn_confirm: Button
        private lateinit var btn_pickDate: Button
        private lateinit var btn_pickTime: Button
        private lateinit var tv_dateSelected: TextView
        private lateinit var tv_timeSelected: TextView
        private lateinit var et_cardTitle: EditText
        private lateinit var et_cardDescription: EditText
        private lateinit var et_point1: EditText
        private lateinit var et_point2: EditText
        private lateinit var et_point3: EditText
        private lateinit var img_1: ImageView
        private lateinit var img_2: ImageView
        private lateinit var img_3: ImageView


        @RequiresApi(Build.VERSION_CODES.N)
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

            btn_confirm = view.findViewById(R.id.btn_confirm)
            btn_pickDate = view.findViewById(R.id.btn_pickDate)
            btn_pickTime = view.findViewById(R.id.btn_pickTime)
            tv_dateSelected = view.findViewById(R.id.tv_dateSelected)
            tv_timeSelected = view.findViewById(R.id.tv_timeSelected)
            img_1 = view.findViewById(R.id.img_1)
            img_2 = view.findViewById(R.id.img_2)
            img_3 = view.findViewById(R.id.img_3)
            et_cardDescription = view.findViewById(R.id.et_cardDescription)
            et_cardTitle = view.findViewById(R.id.et_cardTitle)

            et_point1 = view.findViewById(R.id.et_point1)
            et_point2 = view.findViewById(R.id.et_point2)
            et_point3 = view.findViewById(R.id.et_point3)

            cardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)

            //Add habit to database
            btn_confirm.setOnClickListener {
                addCardToDB()
            }
            //Pick a date and time
            pickDateAndTime()

            //Selected and image to put into our list
            drawableSelected()
        }

        @RequiresApi(Build.VERSION_CODES.N)
        //set on click listeners for our data and time pickers
        private fun pickDateAndTime() {
            btn_pickDate.setOnClickListener {
                getDateCalendar()
                DatePickerDialog(requireContext(), this, year, month, day).show()
            }

            btn_pickTime.setOnClickListener {
                getTimeCalendar()
                TimePickerDialog(context, this, hour, minute, true).show()
            }

        }

        private fun addCardToDB() {

            //Get text from editTexts
            title = et_cardTitle.text.toString()
            description = et_cardDescription.text.toString()
            pt_one = et_point1.text.toString()
            pt_two = et_point2.text.toString()
            pt_three = et_point3.text.toString()

            //Create a timestamp string for our recyclerview
            timeStamp = "$cleanDate $cleanTime"

            //Check that the form is complete before submitting data to the database
            if (!(title.isEmpty() || description.isEmpty() || timeStamp.isEmpty() || drawableSelected == 0)) {
                val card = Card(0, title, description, pt_one, pt_two, pt_three, timeStamp, drawableSelected)

                //add the habit if all the fields are filled
                cardViewModel.addCard(card)
                Toast.makeText(context, "Card created successfully!", Toast.LENGTH_SHORT).show()

                //navigate back to our home fragment
                findNavController().navigate(R.id.action_createCard_to_cards)
            } else {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Create a selector for our icons which will appear in the recycler view
        private fun drawableSelected() {
            img_1.setOnClickListener {
                img_1.isSelected = !img_1.isSelected
                drawableSelected = R.drawable.img_1
                img_1.alpha = 1.0F
                img_2.alpha = 0.3F
                img_3.alpha = 0.3F

                //de-select the other options when we pick an image
                img_2.isSelected = false
                img_3.isSelected = false

            }

            img_2.setOnClickListener {
                img_2.isSelected = !img_2.isSelected
                drawableSelected = R.drawable.img_2
                img_1.alpha = 0.3F
                img_2.alpha = 1.0F
                img_3.alpha = 0.3F

                //de-select the other options when we pick an image
                img_1.isSelected = false
                img_3.isSelected = false
            }

            img_3.setOnClickListener {
                img_3.isSelected = !img_3.isSelected
                drawableSelected = R.drawable.img_3
                img_1.alpha = 0.3F
                img_2.alpha = 0.3F
                img_3.alpha = 1.0F

                //de-select the other options when we pick an image
                img_1.isSelected = false
                img_2.isSelected = false
            }

        }

        //get the time set
        override fun onTimeSet(TimePicker: TimePicker?, p1: Int, p2: Int) {
            Log.d("Fragment", "Time: $p1:$p2")

            cleanTime = Calculations.cleanTime(p1, p2)
            tv_timeSelected.text = "Time: $cleanTime"
        }

        //get the date set
        override fun onDateSet(p0: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {

            cleanDate = Calculations.cleanDate(dayX, monthX, yearX)
            tv_dateSelected.text = "Date: $cleanDate"
        }

        //get the current time
        private fun getTimeCalendar() {
            val cal = Calendar.getInstance()
            hour = cal.get(Calendar.HOUR_OF_DAY)
            minute = cal.get(Calendar.MINUTE)
        }

        //get the current date
        private fun getDateCalendar() {
            val cal = Calendar.getInstance()
            day = cal.get(Calendar.DAY_OF_MONTH)
            month = cal.get(Calendar.MONTH)
            year = cal.get(Calendar.YEAR)
        }
}