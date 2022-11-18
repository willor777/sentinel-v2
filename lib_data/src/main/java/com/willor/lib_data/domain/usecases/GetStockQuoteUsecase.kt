package com.willor.lib_data.domain.usecases

import com.willor.lib_data.data.local.prefs.UserPreferences
import com.willor.lib_data.domain.Repo
import com.willor.lib_data.domain.dataobjs.DataState
import com.willor.lib_data.domain.dataobjs.responses.stock_quote_resp.StockQuote
import kotlinx.coroutines.flow.Flow

class GetStockQuoteUsecase(
    private val repo: Repo
) {

    suspend operator fun invoke(ticker: String): Flow<DataState<StockQuote?>> {
        return repo.getStockQuote(ticker)
    }
}