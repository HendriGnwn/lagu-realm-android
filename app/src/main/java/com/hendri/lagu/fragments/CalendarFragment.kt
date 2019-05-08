package com.hendri.lagu.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hendri.lagu.R
import com.hendri.lagu.activities.CalendarDetailActivity
import kotlinx.android.synthetic.main.fragment_calendar.*

/**
 * Created by hendri on 07/05/19.
 * Copyright (c) 2019. All rights reserved.
 */

class CalendarFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listener()
    }

    private fun listener() {
        calendarView.setOnDateChangeListener { _, y, m, d ->
            val intent = Intent(context, CalendarDetailActivity::class.java)
            val mm = (m+1)
            intent.putExtra("date", "$d/$mm/$y")
            startActivityForResult(intent, 1)
        }
    }
}