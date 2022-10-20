package com.willor.lib_data.domain.usecases

class UseCases(
    val getUserPreferencesUsecase: GetUserPreferencesUsecase,
    val saveUserPreferencesUsecase: SaveUserPreferencesUsecase,
    val getMajorFuturesUsecase: GetMajorFuturesUsecase,
    val getPopularWatchlistOptionsUsecase: GetPopularWatchlistOptionsUsecase,
    val getPopularWatchlistUsecase: GetPopularWatchlistUsecase,
)