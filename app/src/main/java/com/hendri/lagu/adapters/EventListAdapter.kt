package com.hendri.lagu.adapters

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hendri.lagu.R
import com.hendri.lagu.activities.CalendarDetailActivity
import com.hendri.lagu.activities.MainActivity
import com.hendri.lagu.models.ArtistDetail
import com.hendri.lagu.models.ArtistList
import com.hendri.lagu.models.Event
import com.hendri.lagu.models.Wishlist
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.item_artist.view.*
import kotlinx.android.synthetic.main.item_artist.view.parentLayout
import kotlinx.android.synthetic.main.item_event.view.*

/**
 * Created by hendri on 08/05/19.
 * Copyright (c) 2019. All rights reserved.
 */

class EventListAdapter(var events: MutableList<Event?>, val context: Context) : RecyclerView.Adapter<EventListAdapter.ViewHolder>() {
    val realm = Realm.getDefaultInstance()!!

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textId: TextView = itemView.textEventId
        val name: TextView = itemView.textName
        val datetime: TextView = itemView.textDate
        val layoutParent: ConstraintLayout = itemView.parentLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        (context as CalendarDetailActivity)
        val event = events[viewHolder.adapterPosition]
        viewHolder.textId.text = event?.id.toString()
        viewHolder.name.text = event?.name
        viewHolder.datetime.text = event?.time

        if (position % 2 == 0) {
            viewHolder.layoutParent.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGrey))
        } else {
            viewHolder.layoutParent.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
        }
    }

    fun clear() {
        events.clear()
        notifyDataSetChanged()
    }

    fun addAll(list: RealmResults<Event>) {
        this.events = list
        notifyDataSetChanged()
    }

    fun doRemoveWishlist(event: Event?, position: Int) {
        (context as MainActivity)
        notifyItemRemoved(position)
        context.checkSearchList()
    }
}