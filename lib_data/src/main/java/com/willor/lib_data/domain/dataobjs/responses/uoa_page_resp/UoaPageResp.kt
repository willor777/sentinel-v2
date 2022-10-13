package com.willor.lib_data.domain.dataobjs.responses.uoa_page_resp


import com.google.gson.annotations.SerializedName

data class UoaPageResp(
    @SerializedName("data")
    val data: UnusualOptionsPage,
    @SerializedName("lastUpdated")
    val lastUpdated: Long
)