package com.athulvs987.myapplication.api

import com.athulvs987.myapplication.model.newsModel.NewsResponce
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("/v2/top-headlines")
    fun getLatestNews(
        @Query("country")
        country : String,
        @Query("apiKey")
        key : String,
        @Query("pageSize")
        pageSize : Int,
        @Query("page")
        page : Int
    ): Call<NewsResponce>



}