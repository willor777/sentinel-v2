package com.willor.lib_data.data

import com.willor.lib_data.data.local.db.StockDataDb
import com.willor.lib_data.data.local.prefs.DatastorePreferencesManager
import com.willor.lib_data.data.local.prefs.UserPreferences
import com.willor.lib_data.data.remote.StockDataService
import com.willor.lib_data.domain.Repo
import com.willor.lib_data.domain.dataobjs.NetworkState
import com.willor.lib_data.domain.dataobjs.entities.StockChartEntity
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
import com.willor.lib_data.utils.logException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepoImpl(
    private val api: StockDataService,
    private val db: StockDataDb,
    private val prefsManager: DatastorePreferencesManager
): Repo {

    private val tag: String = RepoImpl::class.java.simpleName
    private val chartCacheTimeout = 30000       // 30seconds


    override suspend fun getStockChart(
        ticker: String,
        interval: String,
        periodRange: String,
        prepost: Boolean
    ): Flow<NetworkState<StockChart?>> {

        val checkCache = {

            var returnValue: StockChart? = null

            // Build storage key to use for query
            val chartkey = StockChartEntity.buildStorageKey(
                ticker, interval, periodRange, prepost
            )

            // Query db for matching chart
            val cachedChart = db.getChartTableDao().getById(chartkey)

            // Check the timestamp of chart if not null
            if (cachedChart != null){
                if (System.currentTimeMillis() - cachedChart.timestamp < chartCacheTimeout){
                    returnValue = StockChartEntity.toStockChartResponse(cachedChart)
                }
            }

            returnValue
        }
        val saveChartToCache = {c: StockChart ->

        }

        return flow{

            try{
                emit(NetworkState.Loading())

                // Check for a cached version of chart, return if not null
                val chartCache = checkCache()
                if (chartCache != null){
                    emit(NetworkState.Success(chartCache))
                    return@flow
                }

                // Request new chart
                val chartRequest = api.getStockChart(
                    ticker, interval, periodRange, prepost
                )

                if (chartRequest.isSuccessful){

                    // Verify data
                    val chart = chartRequest.body()
                    if (chart == null){
                        emit(NetworkState.Error("Error: Network Request Failed." +
                                " Null Response Body"))
                        return@flow
                    }

                    // Save chart
                    db.getChartTableDao().insertChart(
                        StockChartEntity.fromStockChartResponse(chartRequest.body()!!)
                    )

                    // Return chart
                    emit(NetworkState.Success(chart))
                }


            }catch (e: Exception){
                logException(tag, e)
                emit(NetworkState.Error("Error: $e"))
            }
        }
    }


    override suspend fun getMajorFutures()
    : Flow<NetworkState<MajorFutures?>> {
        return flow{
            try{
                emit(NetworkState.Loading())

                val resp = api.getMajorFutures()

                if (resp.isSuccessful){
                    emit(NetworkState.Success(resp.body()))
                }

                else{
                    emit(NetworkState.Error("Error: Network Request Failed with" +
                            " code: ${resp.code()}"))
                }

            }catch (e: Exception){
                logException(tag, e)
                emit(NetworkState.Error("Error: $e"))
            }
        }
    }


    override suspend fun getMajorIndices()
    : Flow<NetworkState<MajorIndices?>> {
        return flow{
            try{
                emit(NetworkState.Loading())

                val resp = api.getMajorIndices()

                if (resp.isSuccessful){
                    emit(NetworkState.Success(resp.body()))
                }

                else{
                    emit(NetworkState.Error("Error: Network Request Failed with" +
                            " code: ${resp.code()}"))
                }

            }catch (e: Exception){
                logException(tag, e)
                emit(NetworkState.Error("Error: $e"))
            }
        }
    }


    override suspend fun getStockCompetitors(ticker: String)
    : Flow<NetworkState<StockCompetitors?>> {

        return flow{
            try{
                emit(NetworkState.Loading())

                val resp = api.getStockCompetitors(ticker)

                if (resp.isSuccessful){
                    emit(NetworkState.Success(resp.body()))
                }

                else{
                    emit(NetworkState.Error("Error: Network Request Failed with" +
                            " code: ${resp.code()}"))
                }

            }catch (e: Exception){
                logException(tag, e)
                emit(NetworkState.Error("Error: $e"))
            }
        }
    }


    override suspend fun getStockSnrLevels(ticker: String)
    : Flow<NetworkState<StockSnrLevels?>> {

        return flow{
            try{
                emit(NetworkState.Loading())

                val resp = api.getStockSnrLevels(ticker)

                if (resp.isSuccessful){
                    emit(NetworkState.Success(resp.body()))
                }

                else{
                    emit(NetworkState.Error("Error: Network Request Failed with" +
                            " code: ${resp.code()}"))
                }

            }catch (e: Exception){
                logException(tag, e)
                emit(NetworkState.Error("Error: $e"))
            }
        }
    }


    override suspend fun getOptionsOverview(ticker: String)
    : Flow<NetworkState<OptionsOverview?>> {
        return flow{
            try{
                emit(NetworkState.Loading())

                val resp = api.getOptionsOverview(ticker)

                if (resp.isSuccessful){
                    emit(NetworkState.Success(resp.body()))
                }

                else{
                    emit(NetworkState.Error("Error: Network Request Failed with" +
                            " code: ${resp.code()}"))
                }

            }catch (e: Exception){
                logException(tag, e)
                emit(NetworkState.Error("Error: $e"))
            }
        }
    }


    override suspend fun getUnusualOptionsActivity(page: Int)
    : Flow<NetworkState<UoaPage?>> {

        return flow{
            try{
                emit(NetworkState.Loading())

                val resp = api.getUnusualOptionsActivity(page)

                if (resp.isSuccessful){
                    emit(NetworkState.Success(resp.body()))
                }

                else{
                    emit(NetworkState.Error("Error: Network Request Failed with" +
                            " code: ${resp.code()}"))
                }

            }catch (e: Exception){
                logException(tag, e)
                emit(NetworkState.Error("Error: $e"))
            }
        }
    }


    override suspend fun getStockQuote(ticker: String)
    : Flow<NetworkState<StockQuote?>> {
        return flow{
            try{
                emit(NetworkState.Loading())

                val resp = api.getStockQuote(ticker)

                if (resp.isSuccessful){
                    emit(NetworkState.Success(resp.body()))
                }

                else{
                    emit(NetworkState.Error("Error: Network Request Failed with" +
                            " code: ${resp.code()}"))
                }

            }catch (e: Exception){
                logException(tag, e)
                emit(NetworkState.Error("Error: $e"))
            }
        }
    }


    override suspend fun getEtfQuote(ticker: String)
    : Flow<NetworkState<EtfQuote?>> {

        return flow{
            try{
                emit(NetworkState.Loading())

                val resp = api.getEtfQuote(ticker)

                if (resp.isSuccessful){
                    emit(NetworkState.Success(resp.body()))
                }

                else{
                    emit(NetworkState.Error("Error: Network Request Failed with" +
                            " code: ${resp.code()}"))
                }

            }catch (e: Exception){
                logException(tag, e)
                emit(NetworkState.Error("Error: $e"))
            }
        }
    }


    override suspend fun getAllAvailablePopularWatchlistOptions()
    : Flow<NetworkState<PopularWatchlistOptions?>> {
        return flow{
            try{
                emit(NetworkState.Loading())

                val resp = api.getAllAvailablePopularWatchlistOptions()

                if (resp.isSuccessful){
                    emit(NetworkState.Success(resp.body()))
                }

                else{
                    emit(NetworkState.Error("Error: Network Request Failed with" +
                            " code: ${resp.code()}"))
                }

            }catch (e: Exception){
                logException(tag, e)
                emit(NetworkState.Error("Error: $e"))
            }
        }
    }


    override suspend fun getPopularWatchlist(wlName: String)
    : Flow<NetworkState<PopularWatchlist?>> {
        return flow{
            try{
                emit(NetworkState.Loading())

                val resp = api.getPopularWatchlist(wlName)

                if (resp.isSuccessful){
                    emit(NetworkState.Success(resp.body()))
                }

                else{
                    emit(NetworkState.Error("Error: Network Request Failed with" +
                            " code: ${resp.code()}"))
                }

            }catch (e: Exception){
                logException(tag, e)
                emit(NetworkState.Error("Error: $e"))
            }
        }
    }


    override suspend fun getPopularWatchlistBySearchTags(searchTags: String)
    : Flow<NetworkState<PopularWatchlistSearch?>> {

        return flow{
            try{
                emit(NetworkState.Loading())

                val resp = api.getPopularWatchlistsBySearchTags(searchTags)

                if (resp.isSuccessful){
                    emit(NetworkState.Success(resp.body()))
                }

                else{
                    emit(NetworkState.Error("Error: Network Request Failed with" +
                            " code: ${resp.code()}"))
                }

            }catch (e: Exception){
                logException(tag, e)
                emit(NetworkState.Error("Error: $e"))
            }
        }
    }

    override suspend fun getUserPreferences(): Flow<UserPreferences> {
        return prefsManager.getUserPrefs()
    }

    override suspend fun saveUserPreferences(userPrefs: UserPreferences) {
        prefsManager.saveUserPrefs(userPrefs)
    }
}