package com.willor.lib_data.di

import com.willor.lib_data.data.remote.RetrofitApi
import com.willor.lib_data.data.remote.StockDataService
import com.willor.lib_data.domain.Repo
import com.willor.lib_data.repo.RepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object daggerhiltDI {


    @Singleton
    @Provides
    fun provideStockDataServicde(): StockDataService{
        return RetrofitApi.stockDataService
    }


    @Singleton
    @Provides
    fun provideRepo(stockDataService: StockDataService): Repo {
        return RepoImpl(stockDataService)
    }
}