package com.willor.lib_data.data.remote

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
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


@Suppress("unused")
interface StockDataService {


    @GET("/charts")
    suspend fun getStockChart(
        @Query("ticker") ticker: String,
        @Query("interval") interval: String,
        @Query("periodRange") periodRange: String,
        @Query("prepost") prepost: Boolean
    ): Response<StockChartResp?>


    @GET("/misc/major-futures")
    suspend fun getMajorFutures(): Response<MajorFuturesResp?>


    @GET("/misc/major-indices")
    suspend fun getMajorIndices(): Response<MajorIndicesResp?>


    @GET("/misc/major-competitors")
    suspend fun getStockCompetitors(@Query("ticker") ticker: String): Response<StockCompetitors?>


    @GET("/misc/snr-levels")
    suspend fun getStockSnrLevels(@Query("ticker") ticker: String): Response<StockSnrLevelsResp?>


    @GET("/options/overview")
    suspend fun getOptionsOverview(@Query("ticker") ticker: String): Response<OptionsOverviewResp?>


    @GET("/options/uoa")
    suspend fun getUnusualOptionsActivity(@Query("page") page: Int = 0): Response<UoaPageResp?>


    @GET("/quote/stock")
    suspend fun getStockQuote(@Query("ticker") ticker: String): Response<StockQuoteResp?>


    @GET("/quote/etf")
    suspend fun getEtfQuote(@Query("ticker") ticker: String): Response<EtfQuoteResp?>


    @GET("/watchlists/available-lists")
    suspend fun getAllAvailablePopularWatchlistOptions(): Response<PopularWatchlistOptionsResp?>


    @GET("/watchlists/list")
    suspend fun getPopularWatchlist(@Query("name") wlName: String): Response<PopularWatchlistResp?>


    @GET("/watchlists/search")
    suspend fun getPopularWatchlistsBySearchTags(@Query("searchTags") searchTags: String): Response<PopularWatchlistSearchResp?>

}


fun main() {

    runBlocking {
        val serv = RetrofitApi.stockDataService
    }
}