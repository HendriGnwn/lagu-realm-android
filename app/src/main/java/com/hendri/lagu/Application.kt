package com.hendri.lagu

import com.google.firebase.FirebaseApp
import android.app.Application as BaseApplication
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by hendri on 07/05/19.
 * Copyright (c) 2019. All rights reserved.
 */

class Application : BaseApplication() {

    companion object {
        var TOKEN  = "dd4be2f7a7a256a69354fb8afcf02184"
        var API = "http://api.musixmatch.com/ws/1.1/"
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        Realm.init(this)
        val config = RealmConfiguration.Builder().name("hendrilagu.realm").build()
        Realm.setDefaultConfiguration(config)
    }

}