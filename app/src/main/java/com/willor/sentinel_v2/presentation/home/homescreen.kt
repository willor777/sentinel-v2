package com.willor.sentinel_v2.presentation.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
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
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.willor.sentinel_v2.presentation.home.home_components.*
import com.willor.sentinel_v2.ui.theme.MySizes


const val HOME_TAG = "Homescreen"





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
        homeUiState,
    )


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    homeUiState: State<HomeUiState>,
) {

    Log.i(HOME_TAG, "HomeContent Composed")

    val scaffoldState = rememberScaffoldState(
        rememberDrawerState(initialValue = DrawerValue.Closed)
    )

    val contentScrollState = rememberScrollState()

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

        Column(
            modifier = Modifier.fillMaxWidth().height(1000.dp)
                .verticalScroll(contentScrollState)
        ) {

            FuturesDisplay(
                futuresNetworkState = homeUiState.value.majorFutures,
                onCardClicked = { /*TODO*/ }
            )

            Spacer(Modifier.height(MySizes.VERTICAL_CONTENT_PADDING_SMALL))

//            IndicesDisplay(
//                homeUiState = homeUiState,
//                onCardClicked = { /*TODO*/ }
//            )



            Column(
                modifier = Modifier.fillMaxSize().height(1000.dp)
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
    object LoadCurrentWatchlist : HomeUiEvent()
    class AddTickerToSentinelWatchlist(val ticker: String) : HomeUiEvent()
    class RemoveTickerFromSentinelWatchlist(val ticker: String) : HomeUiEvent()
}