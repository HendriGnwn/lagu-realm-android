package com.hendri.lagu.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.hendri.lagu.R
import com.hendri.lagu.models.ArtistDetail
import com.hendri.lagu.models.Event
import com.hendri.lagu.models.User
import com.hendri.lagu.models.Wishlist
import com.kaopiz.kprogresshud.KProgressHUD
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import org.jetbrains.anko.indeterminateProgressDialog

/**
 * Created by hendri on 07/05/19.
 * Copyright (c) 2019. All rights reserved.
 */

open class BaseActivity : AppCompatActivity() {

    open var progress: KProgressHUD? = null

    open lateinit var realm: Realm
    open lateinit var mGoogleSignInClient: GoogleSignInClient
    open lateinit var mGoogleSignInOptions: GoogleSignInOptions
    open lateinit var firebaseAuth: FirebaseAuth

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    fun initialize() {
        realm = Realm.getDefaultInstance()
        progress = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(false)
            .setLabel("Please Wait")
            .setDimAmount(0.1f)
            .setAnimationSpeed(1)

        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)
    }

    fun user() : User {
        return realm.where<User>().findFirst()!!
    }

    fun showProgress() {
        progress!!.show()
    }

    fun hideProgress() {
        progress!!.dismiss()
    }

    fun unsetRealm() {
        val user = realm.where<User>().findFirst()!!
        var wishlists = realm.where<Wishlist>().findAll()!!
        var events = realm.where<Event>().findAll()!!
        realm.executeTransaction { _ ->
            user.isLogin = false
            user.token = ""
            user.uniqueId = ""
            user.email = ""
            user.fullName = ""
            user.photoUrl = ""

            wishlists.deleteAllFromRealm()
            events.deleteAllFromRealm()
        }
    }

    fun logout() {
        showProgress()
        mGoogleSignInClient.signOut().addOnCompleteListener(this) {
            unsetRealm()
            hideProgress()
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        }
    }
}