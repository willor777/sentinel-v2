package com.willor.lib_data.domain.usecases

import com.willor.lib_data.domain.Repo
import com.willor.lib_data.domain.dataobjs.NetworkState
import com.willor.lib_data.domain.dataobjs.responses.popular_wl_resp.PopularWatchlist
import kotlinx.coroutines.flow.Flow

class GetPopularWatchlistUsecase(
    private val repo: Repo
) {
    suspend operator fun invoke(wlName: String): Flow<NetworkState<PopularWatchlist?>> {
        return repo.getPopularWatchlist((wlName))
    }
}