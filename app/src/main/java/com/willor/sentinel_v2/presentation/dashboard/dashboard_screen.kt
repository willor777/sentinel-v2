@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.willor.sentinel_v2.presentation.dashboard

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.willor.sentinel_v2.presentation.common.NavDrawer
import com.willor.sentinel_v2.presentation.common.NavigationDestinations
import com.willor.sentinel_v2.presentation.common.SentinelWatchlistLazyrow
import com.willor.sentinel_v2.presentation.common.navigationController
import com.willor.sentinel_v2.presentation.dashboard.dash_components.DashCollapsableTopBar
import com.willor.sentinel_v2.presentation.dashboard.dash_components.DashPopularWl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@RootNavGraph(start = true)
@Destination
@Composable
fun DashboardScreen(
    navigator: DestinationsNavigator,
    viewModel: DashboardViewModel = hiltViewModel()
) {

    viewModel.handleEvent(DashboardUiEvent.InitialLoad)

    val uiState = viewModel.uiState.collectAsState()

    Log.d("TESTING", "UI_STATE: $uiState")

    DashboardScreenContent(
        uiStateProvider = { uiState },
        navDrawerDestinationClicked = {
            navigationController(navigator = navigator, destination = it)
        },
        onSettingsIconClicked = {
            navigationController(navigator, NavigationDestinations.Settings)
        },
        onTickerCardClicked = { /*TODO navigate to quote screen*/ },
        onWlOptionClicked = {
            viewModel.handleEvent(DashboardUiEvent.WatchlistOptionClicked(it))
        },
        onAddTickerClick = {
            viewModel.handleEvent(DashboardUiEvent.AddTickerToSentinelWatchlist(it))
        }
        )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreenContent(
    uiStateProvider: () -> State<DashboardUiState>,
    navDrawerDestinationClicked: (NavigationDestinations) -> Unit,
    onSettingsIconClicked: () -> Unit,
    onTickerCardClicked: (ticker: String) -> Unit,
    onWlOptionClicked: (wlName: String) -> Unit,
    onAddTickerClick: (ticker: String) -> Unit,
) {

    val contentScrollState = rememberScrollState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scaffoldState = rememberScaffoldState(drawerState)
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            DashCollapsableTopBar(
                dashUiStateProvider = { uiStateProvider() },
                dashScrollState = { contentScrollState },
                onNavIconClick = {
                    openNavDrawer(coroutineScope, drawerState)
                },
                onSettingsIconClick = onSettingsIconClicked,
                onTickerCardClicked = onTickerCardClicked,
            )
        },
        bottomBar = {},             // TODO Might not use this
        floatingActionButton = {},
        drawerContent = {
            NavDrawer(
                currentDestination = NavigationDestinations.Dashboard,
                destinationClicked = { navDrawerDestinationClicked(it) }
            )
        },
        drawerGesturesEnabled = true
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.80f)
                    .verticalScroll(contentScrollState)
            ) {

                DashPopularWl(
                    dashboardUiStateProvider = uiStateProvider,
                    onTickerCardClicked = onTickerCardClicked,
                    onWlOptionClicked = onWlOptionClicked,
                    onAddTickerClick = onAddTickerClick
                )
            }

            Row(
                modifier = Modifier.fillMaxSize()
            ){
                SentinelWatchlistLazyrow(
                    dashUiStateProvider = uiStateProvider,
                    onRemoveIconClicked = {},
                    onCardClicked = {}
                )
            }



        }       // --- Root Column Content Scope End




    }                       // --- Scaffold Scope End
}


@OptIn(ExperimentalMaterial3Api::class)
private fun openNavDrawer(
    coroutineScope: CoroutineScope,
    drawerState: DrawerState
) = coroutineScope.launch {
    drawerState.open()
}
