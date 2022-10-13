package com.willor.lib_data.domain

import com.willor.lib_data.domain.dataobjs.NetworkState
import com.willor.lib_data.domain.dataobjs.responses.chart_resp.StockChartResp
import com.willor.lib_data.domain.dataobjs.responses.etf_quote_resp.EtfQuoteResp
import com.willor.lib_data.domain.dataobjs.responses.major_futures_resp.MajorFuturesResp
import com.willor.lib_data.domain.dataobjs.responses.major_indices_resp.MajorIndicesResp
import com.willor.lib_data.domain.dataobjs.responses.options_overview_resp.OptionsOverviewResp
import com.willor.lib_data.domain.dataobjs.responses.popular_wl_options_resp.PopularWatchlistOptionsResp
import com.willor.lib_data.domain.dataobjs.responses.popular_wl_resp.PopularWatchlistResp
import com.willor.lib_data.domain.dataobjs.responses.popular_wl_search_resp.PopularWatchlistSearchResp
import com.willor.lib_data.domain.dataobjs.responses.stock_competitors_resp.StockCompetitors
import com.willor.lib_data.domain.dataobjs.responses.stock_quote_resp.StockQuoteResp
import com.willor.lib_data.domain.dataobjs.responses.stock_snr_levels_resp.StockSnrLevelsResp
import com.willor.lib_data.domain.dataobjs.responses.uoa_page_resp.UoaPageResp
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repo {

    suspend
    fun getStockChart(ticker: String, interval: String, periodRange: String, prepost: Boolean)
    : Flow<NetworkState<StockChartResp?>>

    suspend
    fun getMajorFutures()
    : Flow<NetworkState<MajorFuturesResp?>>

    suspend
    fun getMajorIndices()
    : Flow<NetworkState<MajorIndicesResp?>>

    suspend
    fun getStockCompetitors(ticker: String)
    : Flow<NetworkState<StockCompetitors?>>

    suspend
    fun getStockSnrLevels(ticker: String)
    : Flow<NetworkState<StockSnrLevelsResp?>>

    suspend
    fun getOptionsOverview(ticker: String)
    : Flow<NetworkState<OptionsOverviewResp?>>

    suspend
    fun getUnusualOptionsActivity(page: Int = 0)
    : Flow<NetworkState<UoaPageResp?>>

    suspend
    fun getStockQuote(ticker: String)
    : Flow<Response<StockQuoteResp?>>

    suspend
    fun getEtfQuote(ticker: String)
    : Flow<Response<EtfQuoteResp?>>

    suspend
    fun getAllAvailablePopularWatchlistOptions()
    : Flow<Response<PopularWatchlistOptionsResp?>>

    suspend
    fun getPopularWatchlist(wlName: String)
    : Flow<Response<PopularWatchlistResp?>>

    suspend
    fun getPopularWatchlistBySearchTags(searchTags: String)
    : Flow<Response<PopularWatchlistSearchResp?>>
}
