package com.willor.sentinel_v2.presentation.testing

import androidx.compose.runtime.Stable
import com.willor.lib_data.data.local.prefs.UserPreferences
import com.willor.lib_data.domain.dataobjs.DataState
import com.willor.lib_data.domain.dataobjs.responses.etf_quote_resp.EtfQuote
import com.willor.lib_data.domain.dataobjs.responses.major_futures_resp.MajorFutures
import com.willor.lib_data.domain.dataobjs.responses.major_indices_resp.MajorIndices
import com.willor.lib_data.domain.dataobjs.responses.options_overview_resp.OptionsOverview
import com.willor.lib_data.domain.dataobjs.responses.popular_wl_options_resp.PopularWatchlistOptions
import com.willor.lib_data.domain.dataobjs.responses.popular_wl_resp.PopularWatchlist
import com.willor.lib_data.domain.dataobjs.responses.stock_competitors_resp.StockCompetitors
import com.willor.lib_data.domain.dataobjs.responses.stock_quote_resp.StockQuote
import com.willor.lib_data.domain.dataobjs.responses.stock_snr_levels_resp.StockSnrLevels
import com.willor.sentinel_v2.presentation.common.Screens

sealed class UiState{

    @Stable data class DashboardScreenState(
        val screen: Screens = Screens.Dashboard,
        val userPrefs: DataState<UserPreferences> = DataState.Loading(),
        val majorFutures: DataState<MajorFutures?> = DataState.Loading(),
        val majorIndices: DataState<MajorIndices?> = DataState.Loading(),
        val popularWatchlistOptions: DataState<PopularWatchlistOptions?> = DataState.Loading(),
        val popularWatchlistDisplayed: DataState<PopularWatchlist?> = DataState.Loading(),
        ): UiState()

    @Stable data class QuoteScreenState(
        val screen: Screens = Screens.Quotes,
        val userPrefs: DataState<UserPreferences> = DataState.Loading(),
        val currentTicker: String = "",
        val currentSearchText: String = "",
        val currentSearchResults: List<List<String>> = listOf(),
        val stockQuote: DataState<StockQuote?> = DataState.Loading(),
        val etfQuote: DataState<EtfQuote?> = DataState.Loading(),
        val optionsOverview: DataState<OptionsOverview?> = DataState.Loading(),
        val competitors: DataState<StockCompetitors?> = DataState.Loading(),
        val snrLevels: DataState<StockSnrLevels?> = DataState.Loading()
    )
}
