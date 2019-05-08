package com.hendri.lagu.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by hendri on 08/05/19.
 * Copyright (c) 2019. All rights reserved.
 */

open class Wishlist(
    @PrimaryKey var id: Long = 0,
    var artistId: Int = 0,
    var artistName: String = "",
    var artistRating: Int = 0
) : RealmObject() {

}