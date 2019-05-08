package com.hendri.lagu.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.TextView
import com.hendri.lagu.Application
import com.hendri.lagu.R
import com.hendri.lagu.activities.MainActivity
import com.hendri.lagu.adapters.ArtistListAdapter
import com.hendri.lagu.api.Artist
import com.hendri.lagu.models.ArtistList
import com.hendri.lagu.models.Artists
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * Created by hendri on 07/05/19.
 * Copyright (c) 2019. All rights reserved.
 */

class SearchFragment : BaseFragment() {

    private val artist by lazy { Artist.create() }
    private var layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
    private var currentPage : Int = 1
    private val perPage : Int = 100
    private var isSearch : Boolean = false
    private var currentSearch : String = ""
    private var adapter: ArtistListAdapter? = null
    private var listArtist: List<ArtistList?> = arrayListOf()

    private lateinit var mActivity : MainActivity
    private lateinit var artists: Artists


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = (activity as MainActivity)
        listener()
    }

    private fun listener() {
        searchButton.setOnClickListener {
            if(!searchTextInput.text.toString().isEmpty()) {
                searchArtist(searchTextInput.text.toString(), currentPage, perPage)
            }
        }
    }

    fun searchArtist(search: String, currentPage: Int, perPage: Int) {
        if (!isSearch) {
            isSearch = true
            mActivity.showProgress()
            artist.search(search, currentPage, perPage, Application.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        val count = result.message?.body?.artistList?.count()!!
                        if (count > 0) {
//                            totalPage += count
                            initList(result.message.body.artistList, search)
//                            nextPage = (result.message.header?.available!! > totalPage && result.message.header.available > perPage)
                            artists = result
                            currentSearch = search
                        }else{
                            doClearList()
                        }

                        mActivity.hideProgress()
                        isSearch = false
                        Log.e("hendrigunawan", count.toString())
                    },
                    { error ->
                        doClearList()
                        Log.e("hendrigunawan", "${error.message}")
                        mActivity.hideProgress()
                        isSearch = false
                    }
                )
        }
    }

    fun initList(artistList: List<ArtistList?>, search: String) {
        listArtist = artistList
        Log.e("hendrigunawan", adapter.toString())
        if(adapter == null) {
            adapter = ArtistListAdapter(artistList as MutableList<ArtistList?>, context!!)
            Log.e("hendrigunawan", adapter!!.artistList.size.toString())

            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            recyclerView.adapter = adapter
        }else{
            if(currentSearch != search || (artistList.count() == 1 && currentPage == 1)) {
                adapter!!.clear()
            }

            adapter!!.addAll(artistList)
            adapter!!.notifyItemRangeChanged(0, adapter!!.itemCount)
        }
    }

    fun doClearList() {

    }

    fun checkRecordWishlist() {
        listArtist.forEach {
            if (it != null) {
                it.artistDetail?.isWishlist = mActivity.isWishlist(it.artistDetail?.artistId!!)
            }
        }

        // refresh recycler view
        if (adapter != null) {
            adapter!!.notifyDataSetChanged()
        }
    }
}