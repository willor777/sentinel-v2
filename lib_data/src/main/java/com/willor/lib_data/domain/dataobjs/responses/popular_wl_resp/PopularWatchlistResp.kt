package com.willor.lib_data.domain.dataobjs.responses.popular_wl_resp


import com.google.gson.annotations.SerializedName

data class PopularWatchlistResp(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("lastUpdated")
    val lastUpdated: Long
)