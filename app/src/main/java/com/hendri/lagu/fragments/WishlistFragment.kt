package com.hendri.lagu.fragments

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.hendri.lagu.R
import com.hendri.lagu.activities.MainActivity
import com.hendri.lagu.adapters.ArtistListAdapter
import com.hendri.lagu.adapters.WishlistAdapter
import com.hendri.lagu.models.ArtistList
import com.hendri.lagu.models.Artists
import com.hendri.lagu.models.Wishlist
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * Created by hendri on 07/05/19.
 * Copyright (c) 2019. All rights reserved.
 */

class WishlistFragment : BaseFragment() {

    private var layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
    private lateinit var adapter: WishlistAdapter
    private lateinit var mActivity : MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wishlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        mActivity.showProgress()

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        mActivity.hideProgress()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        mActivity = (activity as MainActivity)
        adapter = WishlistAdapter(mActivity.realm.where<Wishlist>().findAll(), this.context!!)

        mActivity.showProgress()
        adapter.addAll(mActivity.wishlists())
        mActivity.hideProgress()
    }

    fun doRefreshWishlist(data: RealmResults<Wishlist>) {
        adapter.addAll(data)
    }
}