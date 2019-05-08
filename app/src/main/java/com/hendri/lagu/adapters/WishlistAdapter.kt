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
import com.hendri.lagu.activities.MainActivity
import com.hendri.lagu.models.ArtistDetail
import com.hendri.lagu.models.ArtistList
import com.hendri.lagu.models.Wishlist
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.item_artist.view.*

/**
 * Created by hendri on 08/05/19.
 * Copyright (c) 2019. All rights reserved.
 */

class WishlistAdapter(var wishlists: MutableList<Wishlist?>, val context: Context) : RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {
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
        return wishlists.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        (context as MainActivity)
        val wishlist = wishlists[viewHolder.adapterPosition]
        viewHolder.artistName.text = wishlist?.artistName
        viewHolder.artistId.text = wishlist?.artistRating.toString()
        Log.e("result", wishlist?.artistName)

        viewHolder.wishlist.text = context.getString(R.string.yes)
        viewHolder.layoutParent.setOnClickListener{
            doRemoveWishlist(wishlist, viewHolder.adapterPosition)
        }

        if (position % 2 == 0) {
            viewHolder.layoutParent.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGrey))
        } else {
            viewHolder.layoutParent.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
        }
    }

    fun clear() {
        wishlists.clear()
        notifyDataSetChanged()
    }

    fun addAll(listWishlist: RealmResults<Wishlist>) {
        this.wishlists = listWishlist
        notifyDataSetChanged()
    }

    fun doRemoveWishlist(wishlist: Wishlist?, position: Int) {
        (context as MainActivity)
        notifyItemRemoved(position)
        context.removeWishlist(wishlist?.artistId!!)
        context.checkSearchList()
    }
}