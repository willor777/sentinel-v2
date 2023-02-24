package com.willor.lib_data.data

import com.willor.lib_data.data.local.db.StockDataDb
import com.willor.lib_data.data.local.prefs.DatastorePreferencesManager
import com.willor.lib_data.data.local.prefs.UserPreferences
import com.willor.lib_data.data.remote.StockDataService
import com.willor.lib_data.domain.Repo
import com.willor.lib_data.domain.dataobjs.DataResourceState
import com.willor.lib_data.domain.dataobjs.UoaFilterOptions
import com.willor.lib_data.domain.dataobjs.entities.StockChartEntity
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
import com.willor.lib_data.utils.logException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class RepoImpl(
    private val api: StockDataService,
    private val db: StockDataDb,
    private val prefsManager: DatastorePreferencesManager
): Repo {

    private val tag: String = RepoImpl::class.java.simpleName
    private val chartCacheTimeout = 30_000       // Charts update every 30s


    override fun getStockChart(
        ticker: String,
        interval: String,
        periodRange: String,
        prepost: Boolean
    ): Flow<DataResourceState<StockChart?>> {


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
                emit(DataResourceState.Loading())

                // Check for a cached version of chart, return if not null
                val chartCache = checkCache()
                if (chartCache != null){
                    emit(DataResourceState.Success(chartCache))
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
                        emit(DataResourceState.Error("Error: Network Request Failed." +
                                " Null Response Body"))
                        return@flow
                    }

                    // Save chart
                    db.getChartTableDao().insertChart(
                        StockChartEntity.fromStockChartResponse(chartRequest.body()!!)
                    )

                    // Return chart
                    emit(DataResourceState.Success(chart))
                }


            }catch (e: Exception){
                logException(tag, e)
                emit(DataResourceState.Error("Error: $e"))
            }
        }
    }


    override fun getMajorFutures()
    : Flow<DataResourceState<MajorFutures?>> {
        return flow{
            try{
                emit(DataResourceState.Loading())

                val resp = api.getMajorFutures()

                if (resp.isSuccessful){
                    emit(DataResourceState.Success(resp.body()))
                }

                else{
                    emit(DataResourceState.Error("Error: Network Request Failed with" +
                            " code: ${resp.code()}"))
                }

            }catch (e: Exception){
                logException(tag, e)
                emit(DataResourceState.Error("Error: $e"))
            }
        }
    }


    override fun getMajorIndices()
    : Flow<DataResourceState<MajorIndices?>> {
        return flow{
            try{
                emit(DataResourceState.Loading())

                val resp = api.getMajorIndices()

                if (resp.isSuccessful){
                    emit(DataResourceState.Success(resp.body()))
                }

                else{
                    emit(DataResourceState.Error("Error: Network Request Failed with" +
                            " code: ${resp.code()}"))
                }

            }catch (e: Exception){
                logException(tag, e)
                emit(DataResourceState.Error("Error: $e"))
            }
        }
    }


    override fun getStockCompetitors(ticker: String)
    : Flow<DataResourceState<StockCompetitors?>> {

        return flow{
            try{
                emit(DataResourceState.Loading())

                val resp = api.getStockCompetitors(ticker)

                if (resp.isSuccessful){
                    emit(DataResourceState.Success(resp.body()))
                }

                else{
                    emit(DataResourceState.Error("Error: Network Request Failed with" +
                            " code: ${resp.code()}"))
                }

            }catch (e: Exception){
                logException(tag, e)
                emit(DataResourceState.Error("Error: $e"))
            }
        }
    }


    override fun getStockSnrLevels(ticker: String)
    : Flow<DataResourceState<StockSnrLevels?>> {

        return flow{
            try{
                emit(DataResourceState.Loading())

                val resp = api.getStockSnrLevels(ticker)

                if (resp.isSuccessful){
                    emit(DataResourceState.Success(resp.body()))
                }

                else{
                    emit(DataResourceState.Error("Error: Network Request Failed with" +
                            " code: ${resp.code()}"))
                }

            }catch (e: Exception){
                logException(tag, e)
                emit(DataResourceState.Error("Error: $e"))
            }
        }
    }


    override fun getOptionsOverview(ticker: String)
    : Flow<DataResourceState<OptionsOverview?>> {
        return flow{
            try{
                emit(DataResourceState.Loading())

                val resp = api.getOptionsOverview(ticker)

                if (resp.isSuccessful){
                    emit(DataResourceState.Success(resp.body()))
                }

                else{
                    emit(DataResourceState.Error("Error: Network Request Failed with" +
                            " code: ${resp.code()}"))
                }

            }catch (e: Exception){
                logException(tag, e)
                emit(DataResourceState.Error("Error: $e"))
            }
        }
    }


    override fun getUnusualOptionsActivity(
        page: Int,
        sortAsc: Boolean,
        sortBy: UoaFilterOptions
    ): Flow<DataResourceState<UoaPage?>> {

        return flow{
            try{
                emit(DataResourceState.Loading())

                val resp = api.getUnusualOptionsActivity(page, sortAsc, sortBy.key)

                if (resp.isSuccessful){
                    emit(DataResourceState.Success(resp.body()))
                }

                else{
                    emit(DataResourceState.Error("Error: Network Request Failed with" +
                            " code: ${resp.code()}"))
                }

            }catch (e: Exception){
                logException(tag, e)
                emit(DataResourceState.Error("Error: $e"))
            }
        }
    }


    override suspend fun getUoa(
        page: Int,
        sortAsc: Boolean,
        sortBy: UoaFilterOptions
    ): UoaPage? {
        try {
            val req = api.getUnusualOptionsActivity(page, sortAsc, sortBy.key)
            if (req.isSuccessful) {
                return req.body()!!
            }
            return null
        } catch (e: Exception) {
            logException(tag, e)
            return null
        }
    }


    override fun getStockQuote(ticker: String)
    : Flow<DataResourceState<StockQuote?>> {
        return flow{
            try{
                emit(DataResourceState.Loading())

                val resp = api.getStockQuote(ticker)

                if (resp.isSuccessful){
                    emit(DataResourceState.Success(resp.body()))
                }

                else{
                    emit(DataResourceState.Error("Error: Network Request Failed with" +
                            " code: ${resp.code()}"))
                }

            }catch (e: Exception){
                logException(tag, e)
                emit(DataResourceState.Error("Error: $e"))
            }
        }
    }


    override fun getEtfQuote(ticker: String)
    : Flow<DataResourceState<EtfQuote?>> {

        return flow{
            try{
                emit(DataResourceState.Loading())

                val resp = api.getEtfQuote(ticker)

                if (resp.isSuccessful){
                    emit(DataResourceState.Success(resp.body()))
                }

                else{
                    emit(DataResourceState.Error("Error: Network Request Failed with" +
                            " code: ${resp.code()}"))
                }

            }catch (e: Exception){
                logException(tag, e)
                emit(DataResourceState.Error("Error: $e"))
            }
        }
    }


    override fun getAllAvailablePopularWatchlistOptions()
    : Flow<DataResourceState<PopularWatchlistOptions?>> {
        return flow{
            try{
                emit(DataResourceState.Loading())

                val resp = api.getAllAvailablePopularWatchlistOptions()

                if (resp.isSuccessful){
                    emit(DataResourceState.Success(resp.body()))
                }

                else{
                    emit(DataResourceState.Error("Error: Network Request Failed with" +
                            " code: ${resp.code()}"))
                }

            }catch (e: Exception){
                logException(tag, e)
                emit(DataResourceState.Error("Error: $e"))
            }
        }
    }


    override fun getPopularWatchlist(wlName: String)
    : Flow<DataResourceState<PopularWatchlist?>> {
        return flow{
            try{
                emit(DataResourceState.Loading())

                val resp = api.getPopularWatchlist(wlName)

                if (resp.isSuccessful){
                    emit(DataResourceState.Success(resp.body()))
                }

                else{
                    emit(DataResourceState.Error("Error: Network Request Failed with" +
                            " code: ${resp.code()}"))
                }

            }catch (e: Exception){
                logException(tag, e)
                emit(DataResourceState.Error("Error: $e"))
            }
        }
    }


    override fun getPopularWatchlistBySearchTags(searchTags: String)
    : Flow<DataResourceState<PopularWatchlistSearch?>> {

        return flow{
            try{
                emit(DataResourceState.Loading())

                val resp = api.getPopularWatchlistsBySearchTags(searchTags)

                if (resp.isSuccessful){
                    emit(DataResourceState.Success(resp.body()))
                }

                else{
                    emit(DataResourceState.Error("Error: Network Request Failed with" +
                            " code: ${resp.code()}"))
                }

            }catch (e: Exception){
                logException(tag, e)
                emit(DataResourceState.Error("Error: $e"))
            }
        }
    }


    override fun getUserPreferences(): Flow<DataResourceState<UserPreferences>> {
        return prefsManager.getUserPrefs()
    }


    override suspend fun saveUserPreferences(userPrefs: UserPreferences) {
        prefsManager.saveUserPrefs(userPrefs)
    }


    override fun getTriggers(
        id: Int?,
        fromTime: Long?,
        toTime: Long?,
        ticker: String?,
        onlyLong: Boolean,
        onlyShort: Boolean
    ): Flow<List<TriggerEntity>?> {

        val dao = db.getTriggerTableDao()

        when{
            (id == null) -> {
                return dao.getAllTriggers()
            }
            else -> {
                return dao.getAllTriggers()
            }
        }
    }


    override suspend fun saveTriggers(vararg triggers: TriggerEntity) =
        withContext(Dispatchers.IO){

            db.getTriggerTableDao().insertAllTriggers(*triggers)
        }

}












