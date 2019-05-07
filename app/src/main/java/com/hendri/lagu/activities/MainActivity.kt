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

        activityMainBinding.isBackButton = View.INVISIBLE
        setAppTitle(getString(R.string.search))

        showProgress()

        var mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(Runnable {
            if (!isFinishing) {
                hideProgress()
            }
        }, 2000)
    }

    private fun setAppTitle(value: String) {
        activityMainBinding.appTitle = value
    }
}
