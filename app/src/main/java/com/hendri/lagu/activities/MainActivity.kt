package com.hendri.lagu.activities

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.hendri.lagu.R
import com.hendri.lagu.databinding.ActivityMainBinding
import com.hendri.lagu.fragments.CalendarFragment
import com.hendri.lagu.fragments.CreateFragment
import com.hendri.lagu.fragments.SearchFragment
import com.hendri.lagu.fragments.WishlistFragment
import com.hendri.lagu.models.ArtistDetail
import com.hendri.lagu.models.Event
import com.hendri.lagu.models.Wishlist
import io.realm.RealmResults
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by hendri on 07/05/19.
 * Copyright (c) 2019. All rights reserved.
 */

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var activityMainBinding: ActivityMainBinding

    private val searchFragment = SearchFragment()
    private val wishlistFragment = WishlistFragment()
    private val calendarFragment = CalendarFragment()
    private val createFragment = CreateFragment()
    private var activedFragment : Fragment = searchFragment
    private val fragmentManager = supportFragmentManager

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.bottom_navigation_search -> {
                setAppTitle(getString(R.string.search))
                fragmentManager.beginTransaction().hide(activedFragment).show(searchFragment).commit()
                activedFragment = searchFragment
                Log.e("hendri", "masuk sini")
                return true
            }

            R.id.bottom_navigation_wishlist -> {
                setAppTitle(getString(R.string.wishlist))
                fragmentManager.beginTransaction().hide(activedFragment).show(wishlistFragment).commit()
                activedFragment = wishlistFragment
                return true
            }

            R.id.bottom_navigation_calendar -> {
                setAppTitle(getString(R.string.calendar))
                fragmentManager.beginTransaction().hide(activedFragment).show(calendarFragment).commit()
                activedFragment = calendarFragment
                return true
            }

            R.id.bottom_navigation_create -> {
                setAppTitle(getString(R.string.create))
                fragmentManager.beginTransaction().hide(activedFragment).show(createFragment).commit()
                activedFragment = createFragment
                return true
            }

            R.id.bottom_navigation_logout -> {
                // logout
                logout()
                return true
            }
        }

        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityMainBinding.executePendingBindings()

        // initial bottom navigation listener
        bottomNavigation.setOnNavigationItemSelectedListener(this)

        fragmentManager.beginTransaction().add(R.id.frameLayout, wishlistFragment, "wishlistFragment").hide(wishlistFragment).commit()
        fragmentManager.beginTransaction().add(R.id.frameLayout, calendarFragment, "calendarFragment").hide(calendarFragment).commit()
        fragmentManager.beginTransaction().add(R.id.frameLayout, createFragment, "createFragment").hide(createFragment).commit()
        fragmentManager.beginTransaction().add(R.id.frameLayout, searchFragment, "searchFragment").commit()

        activityMainBinding.account = this.user().fullName
        setAppTitle(getString(R.string.search))
    }

    private fun setAppTitle(value: String) {
        activityMainBinding.appTitle = value
    }

    fun doRefreshWishlist() {
        wishlistFragment.doRefreshWishlist(this.wishlists())
    }

    fun wishlists() : RealmResults<Wishlist> {
        return realm.where<Wishlist>().findAll()
    }

    fun wishlist(id: Int): Wishlist? {
        return realm.where<Wishlist>().equalTo("artistId", id).findFirst()
    }

    fun isWishlist(id: Int) : Boolean {
        val wishlist = this.wishlist(id)
        if (wishlist == null)
            return false

        return true
    }

    fun createWishlist(artist: ArtistDetail) {
        var wishlist = this.wishlist(artist.artistId!!)
        this.realm.executeTransaction {
            if (wishlist != null) {
                wishlist.deleteFromRealm()
            } else {
                val wishlistReam = realm.createObject<Wishlist>(artist.artistId)
                wishlistReam.artistId = artist?.artistId!!
                wishlistReam.artistName = artist?.artistName!!
                wishlistReam.artistRating = artist?.artistRating!!
            }
        }
    }

    fun removeWishlist(id: Int) {
        val wishlist = this.wishlist(id)
        if(wishlist != null) {
            this.realm.executeTransaction {
                wishlist.deleteFromRealm()
            }
        }
    }

    fun getPrimaryKeyEvent() : Int {
        val primaryKey = realm.where<Event>().max("id")
        if (primaryKey == null) {
            return 1
        }
        return primaryKey.toInt() + 1
    }

    fun checkSearchList() {
        searchFragment.checkRecordWishlist()
    }
}
