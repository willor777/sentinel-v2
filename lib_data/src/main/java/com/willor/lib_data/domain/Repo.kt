package com.willor.lib_data.domain

import com.willor.lib_data.data.local.prefs.UserPreferences
import com.willor.lib_data.domain.dataobjs.NetworkState
import com.willor.lib_data.domain.dataobjs.responses.chart_resp.StockChart
import com.willor.lib_data.domain.dataobjs.responses.etf_quote_resp.EtfQuote
import com.willor.lib_data.domain.dataobjs.responses.major_futures_resp.MajorFutures
import com.willor.lib_data.domain.dataobjs.responses.major_indices_resp.MajorIndices
import com.willor.lib_data.domain.dataobjs.responses.options_overview_resp.OptionsOverview
import com.willor.lib_data.domain.dataobjs.responses.popular_wl_options_resp.PopularWatchlistOptions
import com.willor.lib_data.domain.dataobjs.responses.popular_wl_resp.PopularWatchlist
import com.willor.lib_data.domain.dataobjs.responses.popular_wl_search_resp.PopularWatchlistSearch
import com.willor.lib_data.domain.dataobjs.responses.stock_competitors_resp.StockCompetitors
import com.willor.lib_data.domain.dataobjs.responses.stock_quote_resp.StockQuote
import com.willor.lib_data.domain.dataobjs.responses.stock_snr_levels_resp.StockSnrLevels
import com.willor.lib_data.domain.dataobjs.responses.uoa_page_resp.UoaPage
import kotlinx.coroutines.flow.Flow

interface Repo {

    suspend
    fun getStockChart(ticker: String, interval: String, periodRange: String, prepost: Boolean)
    : Flow<NetworkState<StockChart?>>

    suspend
    fun getMajorFutures()
    : Flow<NetworkState<MajorFutures?>>

    suspend
    fun getMajorIndices()
    : Flow<NetworkState<MajorIndices?>>

    suspend
    fun getStockCompetitors(ticker: String)
    : Flow<NetworkState<StockCompetitors?>>

    suspend
    fun getStockSnrLevels(ticker: String)
    : Flow<NetworkState<StockSnrLevels?>>

    suspend
    fun getOptionsOverview(ticker: String)
    : Flow<NetworkState<OptionsOverview?>>

    suspend
    fun getUnusualOptionsActivity(page: Int = 0)
    : Flow<NetworkState<UoaPage?>>

    suspend
    fun getStockQuote(ticker: String)
    : Flow<NetworkState<StockQuote?>>

    suspend
    fun getEtfQuote(ticker: String)
    : Flow<NetworkState<EtfQuote?>>

    suspend
    fun getAllAvailablePopularWatchlistOptions()
    : Flow<NetworkState<PopularWatchlistOptions?>>

    suspend
    fun getPopularWatchlist(wlName: String)
    : Flow<NetworkState<PopularWatchlist?>>

    suspend
    fun getPopularWatchlistBySearchTags(searchTags: String)
    : Flow<NetworkState<PopularWatchlistSearch?>>

    suspend
    fun getUserPreferences(): Flow<UserPreferences>

    suspend
    fun saveUserPreferences(userPrefs: UserPreferences)
}
