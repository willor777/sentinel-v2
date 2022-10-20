package com.willor.lib_data.data.remote

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
    ): Response<StockChart?>


    @GET("/misc/major-futures")
    suspend fun getMajorFutures(): Response<MajorFutures?>


    @GET("/misc/major-indices")
    suspend fun getMajorIndices(): Response<MajorIndices?>


    @GET("/misc/major-competitors")
    suspend fun getStockCompetitors(@Query("ticker") ticker: String): Response<StockCompetitors?>


    @GET("/misc/snr-levels")
    suspend fun getStockSnrLevels(@Query("ticker") ticker: String): Response<StockSnrLevels?>


    @GET("/options/overview")
    suspend fun getOptionsOverview(@Query("ticker") ticker: String): Response<OptionsOverview?>


    @GET("/options/uoa")
    suspend fun getUnusualOptionsActivity(@Query("page") page: Int = 0): Response<UoaPage?>


    @GET("/quote/stock")
    suspend fun getStockQuote(@Query("ticker") ticker: String): Response<StockQuote?>


    @GET("/quote/etf")
    suspend fun getEtfQuote(@Query("ticker") ticker: String): Response<EtfQuote?>


    @GET("/watchlists/available-lists")
    suspend fun getAllAvailablePopularWatchlistOptions(): Response<PopularWatchlistOptions?>


    @GET("/watchlists/list")
    suspend fun getPopularWatchlist(@Query("name") wlName: String): Response<PopularWatchlist?>


    @GET("/watchlists/search")
    suspend fun getPopularWatchlistsBySearchTags(@Query("searchTags") searchTags: String): Response<PopularWatchlistSearch?>

}


fun main() {

    runBlocking {
        val serv = RetrofitApi.stockDataService
    }
}