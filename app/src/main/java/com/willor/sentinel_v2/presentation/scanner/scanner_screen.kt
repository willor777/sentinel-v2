package com.willor.sentinel_v2.presentation.scanner

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.willor.sentinel_v2.presentation.common.*
import com.willor.sentinel_v2.presentation.scanner.scanner_components.ScannerEvent
import com.willor.sentinel_v2.presentation.scanner.scanner_components.ScannerUiState
import com.willor.sentinel_v2.presentation.scanner.scanner_components.TriggersList
import com.willor.sentinel_v2.presentation.uoa.uoa_components.BasicTopBar
import com.willor.sentinel_v2.ui.theme.MySizes


/*
TODO
    - Ideas...
        - When the user clicks a trigger card and it expands, Show a loading indicator, fetch a
        real time quote to compare the values at the time of the trigger, to the current quote
        values.
        - You Should "Game-ify" this screen.
            - Make it look really good and interactive.
            - Display TONS of information. It would be nice to see a real time log of the
            scanners actions: "Loading stock charts now", "Analyzing AAPL chart with blank analysis"
                - You could accomplish this by saving log strings to the db in the scanner service
                 and observing a flow from the db (automatically emits when db table changes)
 */
@Destination
@Composable
fun ScannerScreen(
    navigator: DestinationsNavigator,
    viewModel: ScannerViewModel = hiltViewModel()
){

    val initial_load_once = remember {
        viewModel.handleEvent(ScannerEvent.InitialLoad)
    }

    val uiState by viewModel.uiState.collectAsState()


    ScannerScreenContent(
        uiStateProvider = { uiState },
        settingsIconClicked = { navController(navigator, Screens.Settings) },
        navDrawerDestinationClicked = {
            navController(navigator, it)
        },
        removeFromWatchlistClicked = {
            // TODO
        },
        watchlistCardClicked = {
            // TODO
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScannerScreenContent(
    uiStateProvider: () -> ScannerUiState,
    settingsIconClicked: () -> Unit,
    navDrawerDestinationClicked: (Screens) -> Unit,
    removeFromWatchlistClicked: (ticker: String) -> Unit,
    watchlistCardClicked: (ticker: String) -> Unit,
){

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scaffoldState = rememberScaffoldState(drawerState)
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            BasicTopBar(
                name = "Sentinel Scanner",
                onNavIconClick = {
                    openNavDrawer(coroutineScope, drawerState)

                },
                onSettingsIconClick = {
                    settingsIconClicked()
                }
            )
        },
        bottomBar = {
            SentinelWatchlistLazyrow(
                tickersList = {
                    uiStateProvider().userPrefs
                },
                onRemoveIconClicked = removeFromWatchlistClicked,
                onCardClicked = watchlistCardClicked
            )
        },             // TODO Might not use this
        floatingActionButton = {},
        drawerContent = {
            NavDrawer(
                currentDestination = Screens.Scanner,
                destinationClicked = { navDrawerDestinationClicked(it) }
            )
        },
        drawerGesturesEnabled = true
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MySizes.HORIZONTAL_EDGE_PADDING),
            verticalArrangement = Arrangement.SpaceBetween
        ){                                                              // Main Column

            TriggersList(uiStateProvider)



        }                                                               // Main Column End
    }
}