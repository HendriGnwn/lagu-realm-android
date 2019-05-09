package com.hendri.lagu.utils

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import java.util.*

/**
 * Created by hendri on 09/056/19.
 * Copyright (c) 2019. All rights reserved.
 */

class NotificationUtil {

    fun setEventNotification(eventId: Long, name: String, timeInMilliSeconds: Long, activity: Activity) {

        //------------  alarm settings start  -----------------//

        if (timeInMilliSeconds > 0) {


            val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(activity.applicationContext, AlarmReceiverUtil::class.java) // AlarmReceiver1 = broadcast receiver

            alarmIntent.putExtra("name", name)
            alarmIntent.putExtra("datetime", timeInMilliSeconds)
            alarmIntent.putExtra("eventId", eventId)


            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timeInMilliSeconds


            val pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        }

        //------------ end of alarm settings  -----------------//


    }
}