package com.willor.lib_data.data.local.prefs

import com.willor.lib_data.utils.gson

data class UserPreferences(
    var sentinelWatchlist: List<String> = listOf(),
    var lastPopularWatchlistSelected: String = "GAINERS"
){
    companion object{
        fun toJson(userPrefs: UserPreferences): String{
            return gson.toJson(userPrefs)
        }

        fun toUserPreferences(jsonString: String): UserPreferences{
            return gson.fromJson(jsonString, UserPreferences::class.java)
        }
    }
}


