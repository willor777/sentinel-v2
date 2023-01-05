package com.willor.sentinel_v2.presentation.quote.quote_components

import androidx.compose.runtime.Stable
import com.willor.lib_data.data.local.prefs.UserPreferences
import com.willor.lib_data.domain.dataobjs.DataState
import com.willor.lib_data.domain.dataobjs.responses.etf_quote_resp.Data
import com.willor.lib_data.domain.dataobjs.responses.etf_quote_resp.EtfQuote
import com.willor.lib_data.domain.dataobjs.responses.options_overview_resp.OptionsOverview
import com.willor.lib_data.domain.dataobjs.responses.stock_competitors_resp.StockCompetitors
import com.willor.lib_data.domain.dataobjs.responses.stock_quote_resp.StockQuote
import com.willor.lib_data.domain.dataobjs.responses.stock_snr_levels_resp.StockSnrLevels

@Stable
data class QuoteUiState(
    val currentTicker: String = "",
    val currentSearchText: String = "",
    val currentSearchResults: List<List<String>> = listOf(),
    val stockQuote: DataState<StockQuote?> = DataState.Loading(),
    val etfQuote: DataState<EtfQuote?> = DataState.Loading(),
    val optionsOverview: DataState<OptionsOverview?> = DataState.Loading(),
    val userPrefs: DataState<UserPreferences> = DataState.Loading(),
    val competitors: DataState<StockCompetitors?> = DataState.Loading(),
    val snrLevels: DataState<StockSnrLevels?> = DataState.Loading()
)
