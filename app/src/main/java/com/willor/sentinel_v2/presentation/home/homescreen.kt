package com.willor.sentinel_v2.presentation.home

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.willor.lib_data.domain.dataobjs.NetworkState
import com.willor.sentinel_v2.presentation.home.home_components.HomeBottomAppBar
import com.willor.sentinel_v2.presentation.home.home_components.HomeFuturesDisplayLazyRow
import com.willor.sentinel_v2.presentation.home.home_components.HomeTopAppBar


const val HOME_TAG = "Homescreen"


// TODO
/*
- Use UI events Sealed Class for interactions with view model like so

sealed class UiEvent{
ButtonClicked: UiEvent()
TextAdded(val txt: String): UiEvent()
}

then in the view model...

fun handleUiEvent(event: UiEvent){
    when (event)
        is UiEvent.ButtonClicked -> {
            do stuff
        }
        is UiEvent.TextAdded -> {
            do stuff with it.txt
        }
}
 */


@Destination(start = true)
@Composable
fun HomeRoute(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {

    Log.i(HOME_TAG, "HomeRoute called. Calling HomeUiEvent.InitialLoad")
    viewModel.handleEvent(HomeUiEvent.InitialLoad)

    val homeUiState = viewModel.uiState.collectAsState()


    HomeContent(
        homeUiState
    )


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    homeUiState: State<HomeUiState>
) {

    Log.i(HOME_TAG, "HomeContent Composed")

    val scaffoldState = rememberScaffoldState(
        rememberDrawerState(initialValue = DrawerValue.Closed)
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            HomeTopAppBar(
                onHomeIconClicked = { /*TODO*/ },
                onSettingsIconClicked = { /*TODO*/ },
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

        FuturesDisplay(
            homeUiState = homeUiState,
            onFutureCardClicked = {
                // TODO
            }
        )

    }

}


@Composable
fun FuturesDisplay(
    homeUiState: State<HomeUiState>,
    onFutureCardClicked: (ticker: String) -> Unit,
) {
    when (val futuresData = homeUiState.value.majorFutures) {
        is NetworkState.Success -> {
            HomeFuturesDisplayLazyRow(
                majorFutures = futuresData.data!!,
                onItemClicked = {
                    onFutureCardClicked(it)
                }
            )
        }
        is NetworkState.Loading -> {
            Text("Futures Data Loading")
        }
        is NetworkState.Error -> {
            Text("Futures Data Failed to load. Error....")
        }
    }
}


sealed class HomeUiEvent() {
    object InitialLoad : HomeUiEvent()
    object LoadFutures : HomeUiEvent()
    object LoadCurrentWatchlist : HomeUiEvent()
    class AddTickerToSentinelWatchlist(val ticker: String) : HomeUiEvent()
    class RemoveTickerFromSentinelWatchlist(val ticker: String) : HomeUiEvent()
}