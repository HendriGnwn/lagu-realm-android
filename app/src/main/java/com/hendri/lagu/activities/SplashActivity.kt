package com.hendri.lagu.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.hendri.lagu.R
import com.hendri.lagu.models.User
import io.realm.kotlin.createObject
import io.realm.kotlin.where

/**
 * Created by hendri on 07/05/19.
 * Copyright (c) 2019. All rights reserved.
 */

class SplashActivity : BaseActivity() {
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 5000

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {

            checkLogin()
        }
    }

    fun checkLogin() {

        if ((realm.where<User>().count()) > 0) {
            val user = realm.where<User>().findFirst()!!

            if(user.isLogin)
                startActivity(Intent(this, MainActivity::class.java))
            else
                startActivity(Intent(this, LoginActivity::class.java))


        }else{
            realm.executeTransaction { _ ->
                val user= realm.createObject<User>(1)
                user.isLogin = false
                user.token = ""
                user.uniqueId = ""
                user.email = ""
                user.fullName = ""
                user.photoUrl = ""
            }

            startActivity(Intent(this, LoginActivity::class.java))
        }

        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initialize()

        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }

}