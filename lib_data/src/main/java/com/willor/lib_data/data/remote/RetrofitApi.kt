package com.willor.lib_data.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApi {

    const val BASE_URL = "https://das-ktstockdata.herokuapp.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val stockDataService = retrofit.create(StockDataService::class.java)


}