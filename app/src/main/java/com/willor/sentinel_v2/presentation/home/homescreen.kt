package com.willor.sentinel_v2.presentation.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.willor.sentinel_v2.presentation.home.home_components.*
import com.willor.sentinel_v2.ui.theme.MySizes
import com.willor.sentinel_v2.ui.theme.SentinelTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive


const val TAG_HOME = "HOME_SCREEN"




@RootNavGraph(start = true)
@Destination
@Composable
fun HomeRoute(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {

    Log.i(TAG_HOME, "HomeRoute called. Calling HomeUiEvent.InitialLoad")
    viewModel.handleEvent(HomeUiEvent.InitialLoad)

    val homeUiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit){
        while(this.isActive){
            delay(5000)
            viewModel.handleEvent(HomeUiEvent.RefreshData)
        }
    }


    SentinelTheme {

        HomeContent(
            homeUiStateProvider = { homeUiState.value },
            onHomeIconClicked = {},
            onSettingsIconClicked = {},
            onFuturesCardClicked = {},
            onIndexCardClicked = {},
            onWatchlistOptionClicked = {
                viewModel.handleEvent(HomeUiEvent.WatchlistOptionClicked(it))
                Log.i(TAG_HOME, "Watchlist Card Clicked: $it")
            },
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    homeUiStateProvider: () -> HomeUiState,
    onHomeIconClicked: () -> Unit,
    onSettingsIconClicked: () -> Unit,
    onFuturesCardClicked: (String) -> Unit,
    onIndexCardClicked: (String) -> Unit,
    onWatchlistOptionClicked: (String) -> Unit,
) {

    Log.i(TAG_HOME, "HomeContent Composed")

    val scaffoldState = rememberScaffoldState(
        rememberDrawerState(initialValue = DrawerValue.Closed)
    )

    val contentScrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            HomeTopAppBar(
                onHomeIconClicked = { onHomeIconClicked() },
                onSettingsIconClicked = { onSettingsIconClicked() },
            )
        },
        bottomBar = {
            HomeBottomAppBar(
                onSearchIconClicked = { /*TODO*/ },
                onWatchlistClicked = { /*TODO*/ },
                onTriggerHistoryClicked = { /*TODO*/ },
                onStatSentinelClicked = { /*TODO*/ },
            )
        },
        floatingActionButton = {
            // TODO Maybe make it a 'Refresh' button
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(1000.dp)
                .verticalScroll(contentScrollState)
                .padding(
                    MySizes.HORIZONTAL_EDGE_PADDING,
                    MySizes.VERTICAL_EDGE_PADDING
                )
        ) {

            FuturesDisplay(
                homeUiStateProvider = homeUiStateProvider,
                onCardClicked = { onFuturesCardClicked(it) }
            )


            IndicesDisplay(
                homeUiStateProvider = homeUiStateProvider,
                onCardClicked = { onIndexCardClicked(it) }
            )


            HomePopularWatchlistDisplay(
                homeUiStateProvider = homeUiStateProvider,
                onOptionClick = { onWatchlistOptionClicked(it) },
                onTickerSearchIconClick = { /*TODO Navigate to quote screen*/ }
            )

            // TODO Scratch Delete This Column
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .height(1000.dp)
                    .background(Color.Blue),
                verticalArrangement = Arrangement.SpaceBetween
            ){
                Text("Extra Space Top")
                Text("Extra Space Bottom")
            }


        }           // Root Layout End -------------------
    }
}







sealed class HomeUiEvent() {
    object InitialLoad : HomeUiEvent()
    object RefreshData: HomeUiEvent()
    class WatchlistOptionClicked(val name: String): HomeUiEvent()
    class AddTickerToSentinelWatchlist(val ticker: String) : HomeUiEvent()
    class RemoveTickerFromSentinelWatchlist(val ticker: String) : HomeUiEvent()
}
