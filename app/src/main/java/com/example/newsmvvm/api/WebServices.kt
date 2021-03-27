package com.example.newsmvvm.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("sources")
    fun getSources(@Query("apiKey")key: String,
                    @Query("language")lang: String,
                    @Query("country")country: String):Call<SourcesResponse>

    @GET("everything")
    fun getNews(@Query("apiKey")key: String,
                   @Query("sources")sources: String,
                   @Query("q")searchKeyWord: String):Call<NewsResponse>
}