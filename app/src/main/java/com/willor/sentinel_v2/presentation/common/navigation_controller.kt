package com.willor.sentinel_v2.presentation.common

import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.willor.sentinel_v2.presentation.destinations.DashboardScreenDestination
import com.willor.sentinel_v2.presentation.destinations.QuoteScreenDestination
import com.willor.sentinel_v2.presentation.destinations.SettingsScreenDestination


fun navController(
    navigator: DestinationsNavigator,
    destination: Screens,
    ticker: String? = null
){
    when (destination){
        Screens.Dashboard -> {
            navigator.navigate(DashboardScreenDestination)
        }
        Screens.Settings -> {
            navigator.navigate(SettingsScreenDestination)
        }
        Screens.Quotes -> {
            navigator.navigate(QuoteScreenDestination(ticker = ticker))
        }
    }

}


enum class Screens(val displayName: String){
    Dashboard("Dashboard"),
    Settings("Settings"),
    Quotes("Quotes"),
    UnusualOptionsActivity("Unusual Option Activity")
}

