package com.example.whattodotomorrow

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.util.*

class TimePickerFragment : DialogFragment(),TimePickerDialog.OnTimeSetListener {
    private lateinit var hourOfDay:String
    private lateinit var minute:String


    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Log.d("hourOfDay",hourOfDay.toString())
        Log.d("minute",minute.toString())
        Log.d("view",view.toString())
        this.hourOfDay=hourOfDay.toString()
        this.minute=minute.toString()
    }

    fun getdata():String{
        return "$hourOfDay:$minute/"
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val calendar = Calendar.getInstance()
        val hour =calendar.get(Calendar.HOUR_OF_DAY)
        val min = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(
            context,
            android.R.style.Theme_Holo_Dialog,
            this,
            hour,
            min,
            true
        )

        return timePickerDialog
    }
}