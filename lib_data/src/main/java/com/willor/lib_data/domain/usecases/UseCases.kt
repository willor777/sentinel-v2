package com.willor.lib_data.domain.usecases

class UseCases(
    val getUserPreferencesUsecase: GetUserPreferencesUsecase,
    val saveUserPreferencesUsecase: SaveUserPreferencesUsecase,
    val getMajorFuturesUsecase: GetMajorFuturesUsecase,
    val getMajorIndicesUsecase: GetMajorIndicesUsecase,
    val getPopularWatchlistOptionsUsecase: GetPopularWatchlistOptionsUsecase,
    val getPopularWatchlistUsecase: GetPopularWatchlistUsecase,
)