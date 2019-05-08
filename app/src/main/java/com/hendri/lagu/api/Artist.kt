package com.hendri.lagu.api

import com.hendri.lagu.Application
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import com.hendri.lagu.models.Artists
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by hendri on 07/05/19.
 * Copyright (c) 2019. All rights reserved.
 */

interface Artist {
    @GET("artist.search")
    @Headers("Content-Type: application/json")
    fun search(@Query("q_artist") keyword: String,
       @Query("page") page: Int,
       @Query("page_size") perPage: Int,
       @Query("apikey") apiKey: String): Observable<Artists>

    companion object {
        fun create(): Artist {
            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Application.API)
                .build().create(Artist::class.java)
        }
    }
}