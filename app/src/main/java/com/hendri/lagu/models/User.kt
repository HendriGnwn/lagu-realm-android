package com.hendri.lagu.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by hendri on 07/05/19.
 * Copyright (c) 2019. All rights reserved.
 */

open class User(
    @PrimaryKey var id: Long = 0,
    var token: String = "",
    var uniqueId: String = "",
    var isLogin: Boolean = false,
    var fullName: String = "",
    var email: String = "",
    var photoUrl: String = ""
) : RealmObject() {

}