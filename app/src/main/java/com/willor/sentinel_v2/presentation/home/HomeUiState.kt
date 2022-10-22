package com.willor.sentinel_v2.presentation.home

import com.willor.lib_data.data.local.prefs.UserPreferences
import com.willor.lib_data.domain.dataobjs.NetworkState
import com.willor.lib_data.domain.dataobjs.responses.major_futures_resp.MajorFutures
import com.willor.lib_data.domain.dataobjs.responses.major_indices_resp.MajorIndices
import com.willor.lib_data.domain.dataobjs.responses.popular_wl_options_resp.PopularWatchlistOptions
import com.willor.lib_data.domain.dataobjs.responses.popular_wl_resp.PopularWatchlist

data class HomeUiState(
    val majorFutures: NetworkState<MajorFutures?> = NetworkState.Loading(),
    val majorIndices: NetworkState<MajorIndices?> = NetworkState.Loading(),
    val popularWatchlistOptions: NetworkState<PopularWatchlistOptions?> = NetworkState.Loading(),
    val popularWatchlistDisplayed: NetworkState<PopularWatchlist?> = NetworkState.Loading(),
    val userPrefs: UserPreferences? = null
)
