package com.hendri.lagu.activities

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.hendri.lagu.R
import com.hendri.lagu.adapters.EventListAdapter
import com.hendri.lagu.models.Event
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_event_notification.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.item_event.*

/**
 * Created by hendri on 08/05/19.
 * Copyright (c) 2019. All rights reserved.
 */

class EventNotificationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_notification)
        initialize()

        init()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    fun init() {
        this.setTitle(intent.getStringExtra("title"))
        val id = intent.getLongExtra("eventId", 0)

        val event = realm.where<Event>().equalTo("id", id).findFirst()
        if (event != null) {
            textEventName.setText(event.name)
            textEventDateTime.setText("${event.date} ${event.time}")
        }
    }
}