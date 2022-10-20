package com.willor.lib_data.domain.usecases

import com.willor.lib_data.domain.Repo
import com.willor.lib_data.domain.dataobjs.NetworkState
import com.willor.lib_data.domain.dataobjs.responses.major_futures_resp.MajorFutures
import kotlinx.coroutines.flow.Flow

class GetMajorFuturesUsecase(
    private val repo: Repo
) {
    suspend operator fun invoke(): Flow<NetworkState<MajorFutures?>> {
        return repo.getMajorFutures()
    }
}