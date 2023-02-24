package com.willor.lib_data.data.local.prefs

import com.willor.lib_data.domain.dataobjs.UoaFilterOptions
import com.willor.lib_data.utils.gson
import com.willor.stock_analysis_lib.analysis.StrategyName
import com.willor.stock_analysis_lib.common.CandleInterval

data class UserPreferences(
    var sentinelWatchlist: List<String> = listOf(),
    var lastPopularWatchlistSelected: String = "GAINERS",
    var uoaSortAsc: Boolean = false,
    var uoaSortBy: UoaFilterOptions = UoaFilterOptions.Volume_OI_Ratio,
    var scannerChartInterval: CandleInterval = CandleInterval.M5,
    var scannerStrategy: StrategyName = StrategyName.TEST_STRATEGY
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


