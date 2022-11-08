package com.willor.lib_data.domain.dataobjs

sealed class DataState<T>{
    class Loading<T>: DataState<T>()
    class Success<T>(val data: T): DataState<T>()
    class Error<T>(val msg: String, val exception: Exception? = null): DataState<T>()
}

