package com.willor.sentinel_v2.presentation.quote

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.willor.sentinel_v2.presentation.common.*
import com.willor.sentinel_v2.presentation.quote.quote_components.QuoteMainContent
import com.willor.sentinel_v2.presentation.quote.quote_components.QuoteTopBar
import com.willor.sentinel_v2.presentation.quote.quote_components.QuoteUiEvent
import com.willor.sentinel_v2.presentation.quote.quote_components.QuoteUiState
import com.willor.sentinel_v2.ui.theme.MySizes


val tag = "QUOTE_SCREEN"

@Destination
@Composable
fun QuoteScreen(
    ticker: String? = null,
    navigator: DestinationsNavigator,
    viewModel: QuoteViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsState()

    if (ticker != null) {
        viewModel.handleUiEvent(QuoteUiEvent.InitialLoad(ticker))
    }

    QuoteContent(
        quoteUiStateProvider = {
            uiState.value
        },
        navDrawerDestinationClicked = {
            navController(navigator, it)
        },
        onSettingsIconClicked = {
            navController(navigator, Screens.Settings)
        },
        onSearchBarTextChange = {
            viewModel.handleUiEvent(QuoteUiEvent.SearchTextUpdated(it))
        },
        onSearchResultClicked = {
            // TODO
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteContent(
    quoteUiStateProvider: () -> QuoteUiState,
    navDrawerDestinationClicked: (Screens) -> Unit,
    onSettingsIconClicked: () -> Unit,
    onSearchBarTextChange: (usrText: String) -> Unit,
    onSearchResultClicked: (ticker: String) -> Unit,
) {

    val navDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scaffoldState = rememberScaffoldState(drawerState = navDrawerState)
    val coroutineScope = rememberCoroutineScope()
    val contentScrollState = rememberScrollState()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            QuoteTopBar(
                onNavIconClick = {
                    Log.d(tag, "Nav icon clicked")
                    openNavDrawer(coroutineScope, navDrawerState)
                },
                onSettingsIconClick = onSettingsIconClicked
            )
        },
        drawerContent = {
            NavDrawer(currentDestination = Screens.Quotes) {
                navDrawerDestinationClicked(it)
            }
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    MySizes.HORIZONTAL_EDGE_PADDING,
                    MySizes.VERTICAL_EDGE_PADDING
                )
        ) {

            // Search bar with drop down search results LazyCol
            TickerSearchBar(
                quoteUiStateProvider = quoteUiStateProvider,
                onTextChange = onSearchBarTextChange,
                onSearchResultClicked = onSearchResultClicked
            )

            // Scrollable Content (Stock/Etf Quote + Options Overview)
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .verticalScroll(contentScrollState)
            ) {

                QuoteMainContent(
                    quoteUiStateProvider = quoteUiStateProvider,
                )


                Column(
                    modifier = Modifier
                        .height(1000.dp)
                        .fillMaxWidth()
                        .background(Color.DarkGray)
                ) {
                    Text(
                        text = "Space Filler Here"
                    )
                }

            }
        }
    }
}






















