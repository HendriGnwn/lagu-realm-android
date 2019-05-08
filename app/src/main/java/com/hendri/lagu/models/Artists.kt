package com.hendri.lagu.models
import com.google.gson.annotations.SerializedName


/**
 * Created by hendri on 07/05/19.
 * Copyright (c) 2019. All rights reserved.
 */
data class Artists(
    @SerializedName("message")
    val message: Message?
)

data class Message(
    @SerializedName("body")
    val body: Body?,
    @SerializedName("header")
    val header: Header?
)

data class Header(
    @SerializedName("available")
    val available: Int?,
    @SerializedName("execute_time")
    val executeTime: Double?,
    @SerializedName("status_code")
    val statusCode: Int?
)

data class Body(
    @SerializedName("artist_list")
    val artistList: List<ArtistList?>?
)

data class ArtistList(
    @SerializedName("artist")
    val artistDetail: ArtistDetail?
)

data class ArtistDetail(
    @SerializedName("artist_alias_list")
    val artistAliasList: List<ArtistAlias?>?,
    @SerializedName("artist_comment")
    val artistComment: String?,
    @SerializedName("artist_country")
    val artistCountry: String?,
    @SerializedName("artist_credits")
    val artistCredits: ArtistCredits?,
    @SerializedName("artist_id")
    val artistId: Int?,
    @SerializedName("artist_name")
    val artistName: String?,
    @SerializedName("artist_name_translation_list")
    val artistNameTranslationList: List<ArtistNameTranslation?>?,
    @SerializedName("artist_rating")
    val artistRating: Int?,
    @SerializedName("artist_twitter_url")
    val artistTwitterUrl: String?,
    @SerializedName("restricted")
    val restricted: Int?,
    @SerializedName("updated_time")
    val updatedTime: String?,
    var isWishlist: Boolean = false
)

data class ArtistCredits(
    @SerializedName("artist_list")
    val artistCreditsList: List<Any?>?
)

data class ArtistNameTranslation(
    @SerializedName("artist_name_translation")
    val artistNameTranslation: ArtistNameTranslationX?
)

data class ArtistNameTranslationX(
    @SerializedName("language")
    val language: String?,
    @SerializedName("translation")
    val translation: String?
)

data class ArtistAlias(
    @SerializedName("artist_alias")
    val artistAlias: String?
)