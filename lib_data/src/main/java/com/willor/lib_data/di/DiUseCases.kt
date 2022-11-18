package com.willor.lib_data.di

import com.willor.lib_data.domain.Repo
import com.willor.lib_data.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DiUseCases {
    
    @Provides
    fun provideGetMajorFuturesUsecase(repo: Repo): GetMajorFuturesUsecase{
        return GetMajorFuturesUsecase(repo)
    }

    @Provides
    fun provideGetMajorIndicesUsecase(repo: Repo): GetMajorIndicesUsecase {
        return GetMajorIndicesUsecase(repo)
    }

    @Provides
    fun provideGetPopularWatchlistOptionsUsecase(repo: Repo): GetPopularWatchlistOptionsUsecase{
        return GetPopularWatchlistOptionsUsecase(repo)
    }

    @Provides
    fun provideGetPopularWatchlistUsecase(repo: Repo): GetPopularWatchlistUsecase{
        return GetPopularWatchlistUsecase(repo)
    }

    @Provides
    fun provideGetUserPreferencesUsecase(repo: Repo): GetUserPreferencesUsecase {
        return GetUserPreferencesUsecase(repo)
    }

    @Provides
    fun provideSaveUserPreferencesUsecase(repo: Repo): SaveUserPreferencesUsecase {
        return SaveUserPreferencesUsecase(repo)
    }

    @Provides
    fun provideGetStockQuoteUsecase(repo: Repo): GetStockQuoteUsecase {
        return GetStockQuoteUsecase(repo)
    }

    @Provides
    fun provideGetEtfQuoteUsecase(repo: Repo): GetEtfQuoteUsecase {
        return GetEtfQuoteUsecase(repo)
    }

    @Provides
    fun provideGetOptionsOverview(repo: Repo): GetOptionsOverviewUsecase {
        return GetOptionsOverviewUsecase(repo)
    }

    @Provides
    fun provideUsecases(
        majorFutures: GetMajorFuturesUsecase,
        majorIndices: GetMajorIndicesUsecase,
        popWlOptions: GetPopularWatchlistOptionsUsecase,
        popWl: GetPopularWatchlistUsecase,
        userPrefs: GetUserPreferencesUsecase,
        saveUserPrefs: SaveUserPreferencesUsecase,
        stockQuote: GetStockQuoteUsecase,
        etfQuote: GetEtfQuoteUsecase,
        options: GetOptionsOverviewUsecase
    ): UseCases{
        return UseCases(
            userPrefs, saveUserPrefs, majorFutures, majorIndices, popWlOptions, popWl,
            stockQuote, etfQuote, options
        )
    }
}