package com.willor.lib_data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object daggerhiltDI {


    @Provides
    fun provideTestString(): String{
        return "TestString from daggerhiltDI.kt"
    }
}