package com.willor.lib_data.repo

import com.willor.lib_data.data.remote.StockDataService
import com.willor.lib_data.domain.Repo
import com.willor.lib_data.domain.dataobjs.NetworkState
import com.willor.lib_data.domain.dataobjs.responses.major_futures_resp.MajorFuturesResp
import com.willor.lib_data.domain.dataobjs.responses.major_indices_resp.MajorIndicesResp
import com.willor.lib_data.domain.dataobjs.responses.stock_competitors_resp.StockCompetitors
import kotlinx.coroutines.flow.Flow

class RepoImpl(
    val stockDataService: StockDataService
): Repo {

    override suspend fun getMajorFutures(): Flow<NetworkState<MajorFuturesResp?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMajorIndices(): Flow<NetworkState<MajorIndicesResp?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getStockCompetitors(ticker: String): Flow<NetworkState<StockCompetitors?>> {
        TODO("Not yet implemented")
    }
}