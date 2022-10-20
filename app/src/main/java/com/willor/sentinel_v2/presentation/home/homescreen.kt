package com.willor.sentinel_v2.presentation.home

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.willor.sentinel_v2.presentation.home.home_components.HomeBottomAppBar
import com.willor.sentinel_v2.presentation.home.home_components.HomeTopAppBar


const val tagLoc = "Homescreen"


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
){

    Log.i(tagLoc, "HomeRoute called. Calling HomeUiEvent.InitialLoad")
    viewModel.handleEvent(HomeUiEvent.InitialLoad)


    HomeContent(

    )


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(

){

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

        Text("Welcome to the home screen")

    }

}


sealed class HomeUiEvent(){
    object InitialLoad: HomeUiEvent()
    object LoadFutures: HomeUiEvent()
    object LoadCurrentWatchlist: HomeUiEvent()
    class AddTickerToSentinelWatchlist(val ticker: String): HomeUiEvent()
    class RemoveTickerFromSentinelWatchlist(val ticker: String): HomeUiEvent()
}