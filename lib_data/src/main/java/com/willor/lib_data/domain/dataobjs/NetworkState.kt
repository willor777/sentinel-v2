package com.willor.lib_data.domain.dataobjs

sealed class NetworkState<T>{
    class Loading<T>: NetworkState<T>()
    class Success<T>(val data: T): NetworkState<T>()
    class Error<T>(val msg: String, val exception: Exception? = null): NetworkState<T>()
}

