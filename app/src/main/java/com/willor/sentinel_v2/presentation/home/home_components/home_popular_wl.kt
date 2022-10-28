package com.willor.sentinel_v2.presentation.home.home_components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.willor.compose_loading_anim.LoadingAnimation
import com.willor.compose_loading_anim.loadLottieFile
import com.willor.lib_data.domain.dataobjs.NetworkState
import com.willor.lib_data.domain.dataobjs.responses.popular_wl_options_resp.PopularWatchlistOptions
import com.willor.lib_data.domain.dataobjs.responses.popular_wl_resp.PopularWatchlist
import com.willor.lib_data.domain.dataobjs.responses.popular_wl_resp.Ticker
import com.willor.sentinel_v2.MainActivity
import com.willor.sentinel_v2.presentation.common.DropdownOptionSelector
import com.willor.sentinel_v2.presentation.common.PopWatchlistLazyCol
import com.willor.sentinel_v2.presentation.home.HomeUiState


@Composable
fun HomePopularWatchlistDisplay(
    homeUiStateProvider: () -> HomeUiState,
    onOptionClick: (String) -> Unit,
    onTickerSearchIconClick: (String) -> Unit
) {

    // Read the HomeUiState here and extract values
    val homeUiState = homeUiStateProvider()

    val wlOptions: NetworkState<PopularWatchlistOptions?> = homeUiState.popularWatchlistOptions

    val wlData: NetworkState<PopularWatchlist?> = homeUiState.popularWatchlistDisplayed

    val curSelectedWlOption = homeUiState.userPrefs?.lastPopularWatchlistSelected ?: "GAINERS"

    // Pass the values in here so that the crossfade controller will recompose on change.
    WatchlistDisplayCrossfadeController(
        wlOptions = wlOptions,
        wlData = wlData,
        curSelectedWl = curSelectedWlOption,
        onOptionClick = onOptionClick,
        onTickerSearchIconClick = onTickerSearchIconClick
    )

}


@Composable
private fun WatchlistDisplayCrossfadeController(
    wlOptions: NetworkState<PopularWatchlistOptions?>,
    wlData: NetworkState<PopularWatchlist?>,
    curSelectedWl: String,
    onOptionClick: (String) -> Unit,
    onTickerSearchIconClick: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Crossfade(targetState = wlData) {

            // Nested When statement (Sloppy i know :P)
            when(wlOptions){        // Check that WL options have loaded
                is NetworkState.Loading -> {
                    WatchlistDisplayLoading()
                }
                is NetworkState.Error -> {
                    WatchlistDisplayError()
                }
                is NetworkState.Success -> {

                    // Check that Watchlist has loaded only after WL options have load success
                    when (it) {
                        is NetworkState.Loading -> {
                            WatchlistDisplayLoading()
                        }
                        is NetworkState.Error -> {
                            WatchlistDisplayError()
                        }
                        is NetworkState.Success -> {

                            WatchlistDisplaySuccess(
                                wlOptions = wlOptions.data!!.data,
                                wlData = it.data!!.data.tickers,
                                curSelectedWl = curSelectedWl,
                                onOptionClick = {option -> onOptionClick(option) },
                                onTickerSearchIconClick = {}
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun WatchlistDisplaySuccess(
    wlOptions: List<String>,
    wlData: List<Ticker>,
    curSelectedWl: String,
    onOptionClick: (String) -> Unit,
    onTickerSearchIconClick: (String) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // Row for watchlist selector (Chip + Dropdown Lazy col)
        WatchlistOptionSelectorRow(
            wlOptions = wlOptions,
            curSelectedWl = curSelectedWl,
            onOptionClick = {
                onOptionClick(it)
            }
        )

        // Watchlist Data Lazy Col
        WatchlistLazyCol(wlData = wlData, onTickerSearchIconClick)
    }


}


@Composable
private fun WatchlistOptionSelectorRow(
    wlOptions: List<String>,
    curSelectedWl: String,
    onOptionClick: (String) -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .wrapContentHeight()

    ) {
        DropdownOptionSelector(
            optionsList = wlOptions,
            curSelection = curSelectedWl,
            onItemClick = {
                onOptionClick(it)
            }
        )
    }

}


@Composable
private fun WatchlistLazyCol(
    wlData: List<Ticker>,
    onTickerSearchIconClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .height(450.dp)
            .fillMaxWidth()
            .background(Color.DarkGray)
    ) {
        PopWatchlistLazyCol(wlData = wlData, onTickerClick = onTickerSearchIconClick)
    }
}




@Composable
private fun WatchlistDisplayLoading(

) {
    val con = LocalContext.current

    LoadingAnimation(
        modifier = Modifier.fillMaxSize(),
        lottieJson = loadLottieFile(con.resources, MainActivity.lottieLoadingJsonId),
        condition = {
            false       // Keep looping until the calling composable changes it.
        },
        onMaxTime = {},
        onConditionTrue = {},
    )
}


@Composable
private fun WatchlistDisplayError(

) {
    val con = LocalContext.current

    LoadingAnimation(
        modifier = Modifier.fillMaxSize(),
        lottieJson = loadLottieFile(con.resources, MainActivity.lottieErrorJsonId),
        condition = {
            false       // Keep looping until the calling composable changes it.
        },
        onMaxTime = {},
        onConditionTrue = {},
    )
}




