package com.willor.lib_data.domain

import com.willor.lib_data.data.local.prefs.UserPreferences
import com.willor.lib_data.domain.dataobjs.DataState
import com.willor.lib_data.domain.dataobjs.UoaFilterOptions
import com.willor.lib_data.domain.dataobjs.entities.TriggerEntity
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

    suspend fun getStockChart(
        ticker: String, interval: String, periodRange: String, prepost: Boolean
    ): Flow<DataState<StockChart?>>

    suspend fun getMajorFutures(): Flow<DataState<MajorFutures?>>

    suspend fun getMajorIndices(): Flow<DataState<MajorIndices?>>

    suspend fun getStockCompetitors(ticker: String): Flow<DataState<StockCompetitors?>>

    suspend fun getStockSnrLevels(ticker: String): Flow<DataState<StockSnrLevels?>>

    suspend fun getOptionsOverview(ticker: String): Flow<DataState<OptionsOverview?>>

    suspend fun getUnusualOptionsActivity(
        page: Int = 0,
        sortAsc: Boolean = true,
        sortBy: UoaFilterOptions = UoaFilterOptions.Volume_OI_Ratio
    ): Flow<DataState<UoaPage?>>

    suspend fun getUoa(
        page: Int = 0,
        sortAsc: Boolean = true,
        sortBy: UoaFilterOptions = UoaFilterOptions.Volume_OI_Ratio
    ): UoaPage?

    suspend fun getStockQuote(ticker: String): Flow<DataState<StockQuote?>>

    suspend fun getEtfQuote(ticker: String): Flow<DataState<EtfQuote?>>

    suspend fun getAllAvailablePopularWatchlistOptions(): Flow<DataState<PopularWatchlistOptions?>>

    suspend fun getPopularWatchlist(wlName: String): Flow<DataState<PopularWatchlist?>>

    suspend fun getPopularWatchlistBySearchTags(searchTags: String): Flow<DataState<PopularWatchlistSearch?>>

    suspend fun getUserPreferences(): Flow<DataState<UserPreferences>>

    suspend fun saveUserPreferences(userPrefs: UserPreferences)


    // TODO Remove This
    suspend fun createDummyTriggers(n: Int)


    fun getTriggers(
        id: Int? = null,
        fromTime: Long? = null,
        toTime: Long? = null,
        ticker: String? = null,
        onlyLong: Boolean = false,
        onlyShort: Boolean = false,
    ): Flow<List<TriggerEntity>?>
}










