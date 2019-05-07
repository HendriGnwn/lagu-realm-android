package com.hendri.lagu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hendri.lagu.R

/**
 * Created by hendri on 07/05/19.
 * Copyright (c) 2019. All rights reserved.
 */

class CalendarFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }
}