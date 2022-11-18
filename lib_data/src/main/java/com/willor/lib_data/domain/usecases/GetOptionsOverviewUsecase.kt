package com.willor.lib_data.domain.usecases

import com.willor.lib_data.domain.Repo
import com.willor.lib_data.domain.dataobjs.DataState
import com.willor.lib_data.domain.dataobjs.responses.options_overview_resp.OptionsOverview
import com.willor.lib_data.domain.dataobjs.responses.stock_quote_resp.StockQuote
import kotlinx.coroutines.flow.Flow

class GetOptionsOverviewUsecase(
    private val repo: Repo
) {

    suspend operator fun invoke(ticker: String): Flow<DataState<OptionsOverview?>> {
        return repo.getOptionsOverview(ticker)
    }
}