package com.willor.lib_data.domain.dataobjs

sealed class NetworkState<T>{
    object Loading: NetworkState<Nothing>()
    class Success<T>(val data: T): NetworkState<T>()
    class Error(val msg: String, val exception: Exception? = null): NetworkState<Nothing>()
}
