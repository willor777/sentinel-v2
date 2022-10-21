package com.willor.lib_data.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitApi {

    const val BASE_URL = "https://das-ktstockdata.herokuapp.com/"

    private val retrofit = Retrofit.Builder()
        .client(
            OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .callTimeout(20000, TimeUnit.MILLISECONDS)
                .followRedirects(true)
                .build()
        )
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val stockDataService = retrofit.create(StockDataService::class.java)


}


fun main() {

}