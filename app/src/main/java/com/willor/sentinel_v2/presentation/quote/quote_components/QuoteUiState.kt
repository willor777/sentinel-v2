package com.willor.sentinel_v2.presentation.quote.quote_components

import androidx.compose.runtime.Stable
import com.willor.lib_data.data.local.prefs.UserPreferences
import com.willor.lib_data.domain.dataobjs.DataState
import com.willor.lib_data.domain.dataobjs.responses.etf_quote_resp.EtfQuote
import com.willor.lib_data.domain.dataobjs.responses.options_overview_resp.OptionsOverview
import com.willor.lib_data.domain.dataobjs.responses.stock_quote_resp.StockQuote

@Stable
data class QuoteUiState(
    val currentTicker: String = "",
    val currentSearchText: String = "",
    val currentSearchResults: List<List<String>> = listOf(),
    val stockQuote: DataState<StockQuote?> = DataState.Loading(),
    val etfQuote: DataState<EtfQuote?> = DataState.Loading(),
    val optionsOverview: DataState<OptionsOverview?> = DataState.Loading(),
    val userPrefs: DataState<UserPreferences> = DataState.Loading()
)
