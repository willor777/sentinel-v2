package com.willor.lib_data.data.remote

import com.willor.lib_data.domain.dataobjs.UoaFilterOptions
import com.willor.lib_data.domain.dataobjs.post_bodies.LoginUserBody
import com.willor.lib_data.domain.dataobjs.post_bodies.RegisterNewUserBody
import com.willor.lib_data.domain.dataobjs.responses.authentication_resp.LoginResponse
import com.willor.lib_data.domain.dataobjs.responses.authentication_resp.RegistrationResponse
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
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


@Suppress("unused")
interface StockDataService {


    // TODO Both below methods take the same 2 query params
    //  email and password
    @POST("/auth/register")
    suspend fun registerNewUser(
        @Body registerNewUserBody: RegisterNewUserBody
    ): Response<RegistrationResponse>

    @POST("/auth/login")
    suspend fun loginUser(
        @Body loginUserBody: LoginUserBody
    ): Response<LoginResponse>

    @GET("/auth-v1/charts")
    suspend fun getStockChart(
        @Query("ticker") ticker: String,
        @Query("interval") interval: String,
        @Query("periodRange") periodRange: String,
        @Query("prepost") prepost: Boolean
    ): Response<StockChart?>


    @GET("/auth-v1/misc/major-futures")
    suspend fun getMajorFutures(): Response<MajorFutures?>


    @GET("/auth-v1/misc/major-indices")
    suspend fun getMajorIndices(): Response<MajorIndices?>


    @GET("/auth-v1/misc/major-competitors")
    suspend fun getStockCompetitors(@Query("ticker") ticker: String): Response<StockCompetitors?>


    @GET("/auth-v1/misc/snr-levels")
    suspend fun getStockSnrLevels(@Query("ticker") ticker: String): Response<StockSnrLevels?>


    @GET("/auth-v1/options/overview")
    suspend fun getOptionsOverview(@Query("ticker") ticker: String): Response<OptionsOverview?>


    @GET("/auth-v1/options/uoa")
    suspend fun getUnusualOptionsActivity(
        @Query("page") page: Int = 0,
        @Query("sort_asc") sortAsc: Boolean = true,
        @Query("sort_by") sortBy: String
    ): Response<UoaPage?>


    @GET("/auth-v1/quote/stock")
    suspend fun getStockQuote(@Query("ticker") ticker: String): Response<StockQuote?>


    @GET("/auth-v1/quote/etf")
    suspend fun getEtfQuote(@Query("ticker") ticker: String): Response<EtfQuote?>


    @GET("/auth-v1/watchlists/available-lists")
    suspend fun getAllAvailablePopularWatchlistOptions(): Response<PopularWatchlistOptions?>


    @GET("/auth-v1/watchlists/list")
    suspend fun getPopularWatchlist(@Query("name") wlName: String): Response<PopularWatchlist?>


    @GET("/auth-v1/watchlists/search")
    suspend fun getPopularWatchlistsBySearchTags(@Query("searchTags") searchTags: String): Response<PopularWatchlistSearch?>

}

