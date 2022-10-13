package com.willor.lib_data.data.remote

import retrofit2.http.GET


interface StockDataService {



    @GET("/charts")
    fun getCharts()
}