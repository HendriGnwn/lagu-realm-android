package com.hendri.lagu.activities

import android.support.v7.app.AppCompatActivity
import io.realm.Realm

open class BaseActivity : AppCompatActivity() {
    open lateinit var realm: Realm
}