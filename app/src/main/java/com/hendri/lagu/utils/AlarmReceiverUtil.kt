package com.hendri.lagu.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Created by hendri on 09/05/19.
 * Copyright (c) 2019. All rights reserved.
 */

class AlarmReceiverUtil : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val service = Intent(context, NotificationServiceUtil::class.java)
        service.putExtra("name", intent.getStringExtra("name"))
        service.putExtra("datetime", intent.getLongExtra("datetime", 0))
        service.putExtra("eventId", intent.getLongExtra("eventId", 0))

        service.data = Uri.parse("custom://" + System.currentTimeMillis())
        context.startService(service)
    }

}
