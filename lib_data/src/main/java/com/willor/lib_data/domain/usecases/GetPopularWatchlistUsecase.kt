package com.willor.lib_data.domain.usecases

import com.willor.lib_data.domain.Repo
import com.willor.lib_data.domain.dataobjs.DataState
import com.willor.lib_data.domain.dataobjs.responses.popular_wl_resp.PopularWatchlist
import kotlinx.coroutines.flow.Flow

class GetPopularWatchlistUsecase(
    private val repo: Repo
) {
    suspend operator fun invoke(wlName: String): Flow<DataState<PopularWatchlist?>> {
        return repo.getPopularWatchlist((wlName))
    }
}