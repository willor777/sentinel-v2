package com.willor.lib_data.domain.usecases

import com.willor.lib_data.domain.Repo
import com.willor.lib_data.domain.dataobjs.DataState
import com.willor.lib_data.domain.dataobjs.responses.stock_snr_levels_resp.StockSnrLevels
import kotlinx.coroutines.flow.Flow

class GetSnrLevelsUsecase(
    private val repo: Repo
) {

    suspend operator fun invoke(ticker: String): Flow<DataState<StockSnrLevels?>> {
        return repo.getStockSnrLevels(ticker)
    }
}