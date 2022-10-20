package com.willor.lib_data.domain.usecases

import com.willor.lib_data.domain.Repo
import com.willor.lib_data.domain.dataobjs.NetworkState
import com.willor.lib_data.domain.dataobjs.responses.popular_wl_options_resp.PopularWatchlistOptions
import kotlinx.coroutines.flow.Flow

class GetPopularWatchlistOptionsUsecase(
    private val repo: Repo
) {

    suspend operator fun invoke(): Flow<NetworkState<PopularWatchlistOptions?>> {
        return repo.getAllAvailablePopularWatchlistOptions()
    }
}