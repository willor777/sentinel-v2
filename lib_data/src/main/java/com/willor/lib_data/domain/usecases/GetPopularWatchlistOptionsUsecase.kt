package com.willor.lib_data.domain.usecases

import com.willor.lib_data.domain.Repo
import com.willor.lib_data.domain.dataobjs.DataState
import com.willor.lib_data.domain.dataobjs.responses.popular_wl_options_resp.PopularWatchlistOptions
import kotlinx.coroutines.flow.Flow

class GetPopularWatchlistOptionsUsecase(
    private val repo: Repo
) {

    suspend operator fun invoke(): Flow<DataState<PopularWatchlistOptions?>> {
        return repo.getAllAvailablePopularWatchlistOptions()
    }
}