package com.willor.lib_data.domain.usecases

import com.willor.lib_data.data.local.prefs.UserPreferences
import com.willor.lib_data.domain.Repo
import kotlinx.coroutines.flow.Flow

class GetUserPreferencesUsecase(
    private val repo: Repo
) {

    suspend operator fun invoke(): Flow<UserPreferences> {
        return repo.getUserPreferences()
    }
}