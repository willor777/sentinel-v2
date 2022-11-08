package com.willor.sentinel_v2.presentation.common

import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.willor.sentinel_v2.presentation.destinations.DashboardScreenDestination
import com.willor.sentinel_v2.presentation.destinations.SettingsScreenDestination


fun navigationController(
    navigator: DestinationsNavigator,
    destination: NavigationDestinations
){
    when (destination){
        NavigationDestinations.Dashboard -> {
            navigator.navigate(DashboardScreenDestination)
        }
        NavigationDestinations.Settings -> {
            navigator.navigate(SettingsScreenDestination)
        }
    }

}


enum class NavigationDestinations(val displayName: String){
    Dashboard("Dashboard"),
    Settings("Settings"),
    Quotes("Quotes"),
    UnusualOptionsActivity("Unusual Option Activity")
}

