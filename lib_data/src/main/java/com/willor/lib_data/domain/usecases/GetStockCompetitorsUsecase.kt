package com.willor.lib_data.domain.usecases

import com.willor.lib_data.domain.Repo
import com.willor.lib_data.domain.dataobjs.DataState
import com.willor.lib_data.domain.dataobjs.responses.stock_competitors_resp.StockCompetitors
import kotlinx.coroutines.flow.Flow

class GetStockCompetitorsUsecase(
    val repo: Repo
) {

    suspend operator fun invoke(ticker: String): Flow<DataState<StockCompetitors?>> =
        repo.getStockCompetitors(ticker)
}