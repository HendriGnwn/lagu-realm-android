package com.hendri.lagu.adapters

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hendri.lagu.R
import com.hendri.lagu.activities.MainActivity
import com.hendri.lagu.models.ArtistDetail
import com.hendri.lagu.models.ArtistList
import com.hendri.lagu.models.Wishlist
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.item_artist.view.*

/**
 * Created by hendri on 07/05/19.
 * Copyright (c) 2019. All rights reserved.
 */

class ArtistListAdapter(var artistList: MutableList<ArtistList?>, val context: Context) : RecyclerView.Adapter<ArtistListAdapter.ViewHolder>() {
    val realm = Realm.getDefaultInstance()!!

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val artistName: TextView = itemView.textArtistName
        val artistId: TextView = itemView.textId
        val wishlist: TextView = itemView.textWishlist
        val layoutParent: ConstraintLayout = itemView.parentLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_artist, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return artistList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        (context as MainActivity)
        val artist = artistList[viewHolder.adapterPosition]?.artistDetail
        viewHolder.artistName.text = artist?.artistName
        viewHolder.artistId.text = artist?.artistRating.toString()
        Log.e("result", artist?.artistName)

        if (artist?.isWishlist!!) {
            viewHolder.wishlist.text = context.getString(R.string.yes)
        } else {
            viewHolder.wishlist.text = context.getString(R.string.no)
        }

        viewHolder.layoutParent.setOnClickListener{
            artist.isWishlist = !artist.isWishlist
            doAddWishlist(artist, viewHolder.adapterPosition)
        }

        if (position % 2 == 0) {
            viewHolder.layoutParent.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGrey))
        } else {
            viewHolder.layoutParent.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
        }

    }

    fun clear() {
        artistList.clear()
        notifyDataSetChanged()
    }

    fun addAll(artist: List<ArtistList?>) {
        artistList.addAll(artist)
        notifyDataSetChanged()
    }

    fun doAddWishlist(artist: ArtistDetail, position: Int) {
        (context as MainActivity)
        context.createWishlist(artist)
        context.doRefreshWishlist()
        notifyItemChanged(position)
    }
}