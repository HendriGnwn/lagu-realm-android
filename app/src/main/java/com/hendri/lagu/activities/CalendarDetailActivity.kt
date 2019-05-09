package com.hendri.lagu.activities

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.hendri.lagu.R
import com.hendri.lagu.adapters.EventListAdapter
import com.hendri.lagu.models.Event
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * Created by hendri on 08/05/19.
 * Copyright (c) 2019. All rights reserved.
 */

class CalendarDetailActivity : BaseActivity() {

    private lateinit var selectedDate: String
    private var adapter: EventListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_detail)
        initialize()

        init()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

    }

    fun init() {
        selectedDate = intent.getStringExtra("date")

        this.setTitle(selectedDate)

        showProgress()
        val events = realm.where<Event>().equalTo("date", selectedDate).findAll()
        adapter = EventListAdapter(events, this)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recyclerView.adapter = adapter

        hideProgress()
    }
}