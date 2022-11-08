package com.willor.sentinel_v2.presentation.dashboard

sealed interface DashboardUiEvent{
    object InitialLoad : DashboardUiEvent
    object RefreshData: DashboardUiEvent
    data class WatchlistOptionClicked(val name: String): DashboardUiEvent
    data class AddTickerToSentinelWatchlist(val ticker: String) : DashboardUiEvent
    data class RemoveTickerFromSentinelWatchlist(val ticker: String) : DashboardUiEvent
}