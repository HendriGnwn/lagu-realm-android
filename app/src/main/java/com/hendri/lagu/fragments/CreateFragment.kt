package com.hendri.lagu.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import java.util.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.hendri.lagu.R
import com.hendri.lagu.R.id.editTextDate
import com.hendri.lagu.R.id.editTextName
import com.hendri.lagu.activities.MainActivity
import com.hendri.lagu.models.Event
import io.realm.kotlin.createObject
import kotlinx.android.synthetic.main.fragment_create.*
import java.text.SimpleDateFormat

/**
 * Created by hendri on 07/05/19.
 * Copyright (c) 2019. All rights reserved.
 */

class CreateFragment : BaseFragment() {

    private lateinit var datePicker: DatePickerDialog
    private lateinit var mActivity : MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = (activity as MainActivity)
        init()

        listener()
    }

    private fun listener() {
        editTextDate.setOnClickListener {
            datePicker.show()
        }

        editTextDate.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                datePicker.show()
            }
        }

        saveButton.setOnClickListener {
            Log.e("hendrigunawan", "onclick")
            doSaveEvent()
        }

        datePicker.setOnCancelListener {
            editTextDate.setText("")
        }
    }

    private fun init() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val timePickerDialog = TimePickerDialog(context,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                editTextDate.setText(editTextDate.text.toString() + " $hourOfDay:$minute")
            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true)

        timePickerDialog.setOnCancelListener {
            editTextDate.setText("")
        }

        datePicker = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, y, m, d ->
            val mm = m + 1
            editTextDate.setText("$d/$mm/$y")
            timePickerDialog.show()
        }, year, month, day)

        datePicker.datePicker.minDate = System.currentTimeMillis() - 1000
    }

    private fun doSaveEvent() {
        val name = editTextName.text.toString()
        val date = editTextDate.text.toString()

        Log.e("hendrigunawan", name)
        Log.e("hendrigunawan", editTextDate.text.toString())

        if(name.isEmpty() && date.isEmpty()) {
            Toast.makeText(context, "Name and Date cannot be empty", Toast.LENGTH_LONG).show()
        }else if(name.isEmpty()) {
            Toast.makeText(context, "Name cannot be empty", Toast.LENGTH_LONG).show()
        }else if(date.isEmpty()) {
            Toast.makeText(context, "Date cannot be empty", Toast.LENGTH_LONG).show()
        }else{
            val simpleDateFormat = SimpleDateFormat("d/M/y k:m")
            val dateTime = simpleDateFormat.parse(date)
            val currentDateTime = simpleDateFormat.parse(simpleDateFormat.format(Calendar.getInstance().time))

            if (dateTime.compareTo(currentDateTime) >= 0) {
                mActivity.showProgress()
                mActivity.realm.executeTransaction { _ ->
                    val id = mActivity.getPrimaryKeyEvent()
                    val event = mActivity.realm.createObject<Event>(id)
                    val datetime = editTextDate.text.toString().split(" ")

                    event.name = editTextName.text.toString()
                    event.date = datetime[0]
                    event.time = datetime[1]

                    editTextName.setText("")
                    editTextDate.setText("")
                    Toast.makeText(context, "Event Successfully saved", Toast.LENGTH_LONG).show()
                }

                mActivity.hideProgress()
            }else{
                Toast.makeText(context, "Date time must be greater than current date time", Toast.LENGTH_LONG).show()
            }
        }
    }
}