package com.willor.sentinel_v2.presentation.home.home_components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.willor.compose_loading_anim.LoadingAnimation
import com.willor.compose_loading_anim.loadLottieFile
import com.willor.lib_data.domain.dataobjs.NetworkState
import com.willor.lib_data.domain.dataobjs.responses.major_futures_resp.MajorFutures
import com.willor.sentinel_v2.MainActivity
import com.willor.sentinel_v2.presentation.common.StateOfDisplay
import com.willor.sentinel_v2.presentation.common.TickerCardLazyRow
import com.willor.sentinel_v2.ui.theme.MySizes



@Composable
fun FuturesDisplay(
    futuresNetworkState: NetworkState<MajorFutures?>,
    onCardClicked: (ticker: String) -> Unit,
) {

    var currentState by remember {
        mutableStateOf(StateOfDisplay.Loading)
    }

    currentState = when (futuresNetworkState){
        is NetworkState.Success -> {
            StateOfDisplay.Success
        }
        is NetworkState.Loading -> {
            StateOfDisplay.Loading
        }
        is NetworkState.Error -> {
            StateOfDisplay.Error
        }
    }

    FuturesCrossfadeAnimationController(currentState, futuresNetworkState, onCardClicked)
}

@Composable
private fun FuturesCrossfadeAnimationController(
    currentState: StateOfDisplay,
    futuresNetworkState: NetworkState<MajorFutures?>,
    onCardClicked: (ticker: String) -> Unit
) {

    Column(
        modifier = Modifier.height(90.dp).fillMaxWidth()
            .padding(
                top = MySizes.VERTICAL_EDGE_PADDING,
//                bottom = MySizes.VERTICAL_CONTENT_PADDING_MEDIUM,
            ),
        verticalArrangement = Arrangement.SpaceAround
    ){

        // Header Text "Futures"
        Text(
            text = "Futures",
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            Modifier.height(
                MySizes.VERTICAL_CONTENT_PADDING_SMALL
            )
        )

        Spacer(
            Modifier.height(1.dp).fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.primary
                )
        )

        Spacer(
            Modifier.height(
                MySizes.VERTICAL_CONTENT_PADDING_SMALL
            )
        )

        // Content
        Crossfade(targetState = currentState) {

            when (currentState) {

                // Successful Load
                StateOfDisplay.Success -> {
                    FuturesDisplaySuccess(
                        majorFutures = (futuresNetworkState as NetworkState.Success).data!!,
                        onItemClicked = { ticker ->
                            onCardClicked(ticker)
                        }
                    )
                }

                // Load In Progress
                StateOfDisplay.Loading -> {
                    FuturesDisplayLoading()
                }

                // Load Failed
                StateOfDisplay.Error -> {
                    FuturesDisplayError()
                }
            }
        }

    }
}


@Composable
fun FuturesDisplaySuccess(
    majorFutures: MajorFutures,
    onItemClicked: (ticker: String) -> Unit
){

    // Build data for the TickerCardLazyrow Composable
    val tickerList = mutableListOf<String>()
    val gainDollarList = mutableListOf<Double>()
    val gainPctList = mutableListOf<Double>()
    majorFutures.data.forEach {
        tickerList.add(it.name)
        gainDollarList.add(it.changeDollar)
        gainPctList.add(it.changePercent)
    }

        TickerCardLazyRow(
            tickerList = tickerList,
            gainDollarList = gainDollarList,
            gainPctList = gainPctList,
            onItemClicked = { ticker -> onItemClicked(ticker) }
        )
}


@Composable
fun FuturesDisplayLoading(){
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
fun FuturesDisplayError(){

    val con = LocalContext.current

        LoadingAnimation(
            modifier = Modifier.fillMaxSize(),
            loadLottieFile(con.resources, MainActivity.lottieErrorJsonId),
            condition = {
                false           // Keep looping until the calling composable changes it.
            },
            onMaxTime = {},
            onConditionTrue = {},
        )
}

