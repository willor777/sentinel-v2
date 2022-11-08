package com.willor.sentinel_v2.presentation.dashboard

import com.willor.lib_data.data.local.prefs.UserPreferences
import com.willor.lib_data.domain.dataobjs.DataState
import com.willor.lib_data.domain.dataobjs.responses.major_futures_resp.MajorFutures
import com.willor.lib_data.domain.dataobjs.responses.major_indices_resp.MajorIndices
import com.willor.lib_data.domain.dataobjs.responses.popular_wl_options_resp.PopularWatchlistOptions
import com.willor.lib_data.domain.dataobjs.responses.popular_wl_resp.PopularWatchlist

data class DashboardUiState(
    val majorFutures: DataState<MajorFutures?> = DataState.Loading(),
    val majorIndices: DataState<MajorIndices?> = DataState.Loading(),
    val popularWatchlistOptions: DataState<PopularWatchlistOptions?> = DataState.Loading(),
    val popularWatchlistDisplayed: DataState<PopularWatchlist?> = DataState.Loading(),
    val userPrefs: UserPreferences? = null
)