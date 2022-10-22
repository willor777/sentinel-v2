package com.willor.sentinel_v2.presentation.home.home_components

import androidx.compose.animation.Crossfade
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
import com.willor.lib_data.domain.dataobjs.responses.major_indices_resp.MajorIndices
import com.willor.sentinel_v2.MainActivity
import com.willor.sentinel_v2.presentation.common.TickerCardLazyRow
import com.willor.sentinel_v2.presentation.home.HomeUiState
import com.willor.sentinel_v2.ui.theme.MySizes


@Composable
fun IndicesDisplay(
    homeUiState: State<HomeUiState>,
    onCardClicked: (ticker: String) -> Unit
){
    var loadState by remember {
        mutableStateOf("LOADING")
    }

    loadState = when (homeUiState.value.majorIndices){

        is NetworkState.Loading -> {
            "LOADING"
        }

        is NetworkState.Success -> {
            "SUCCESS"
        }

        is NetworkState.Error -> {
            "ERROR"
        }

        else -> {
            "LOADING"
        }
    }

    Crossfade(targetState = loadState) {indicesLoadState ->
        when (indicesLoadState){
            "LOADING" -> {
                IndicesDisplayLoading()
            }
            "SUCCESS" -> {
                IndicesDisplaySuccess(
                    majorIndices = (homeUiState.value.majorIndices as NetworkState.Success).data!!,
                    onCardClicked = {
                        onCardClicked(it)
                    }
                )
            }
            "ERROR" -> {
                IndicesDisplayError()
            }
        }
    }

}


@Composable
fun IndicesDisplaySuccess(
    majorIndices: MajorIndices,
    onCardClicked: (ticker: String) -> Unit
){
    // Build data for the TickerCardLazyrow Composable
    val tickerList = mutableListOf<String>()
    val gainDollarList = mutableListOf<Double>()
    val gainPctList = mutableListOf<Double>()
    majorIndices.data.forEach {
        tickerList.add(it.name)
        gainDollarList.add(it.changeDollar)
        gainPctList.add(it.changePercent)
    }

    Column(
        modifier = Modifier
            .padding(0.dp, MySizes.VERTICAL_CONTENT_PADDING_SMALL)
            .fillMaxWidth()
            .fillMaxHeight(.15f),
    ) {

        Text(
            text = "World Indices",
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
            color = MaterialTheme.colorScheme.onBackground
        )

        TickerCardLazyRow(
            tickerList = tickerList,
            gainDollarList = gainDollarList,
            gainPctList = gainPctList,
            onItemClicked = { ticker -> onCardClicked(ticker) }
        )
    }
}


@Composable
fun IndicesDisplayLoading(){
    val con = LocalContext.current

    Column(
        modifier = Modifier
            .padding(0.dp, MySizes.VERTICAL_CONTENT_PADDING_SMALL)
            .fillMaxWidth()
            .fillMaxHeight(.10f),
    ) {
        Text(
            text = "World Indices",
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
            color = MaterialTheme.colorScheme.onBackground
        )

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
}


@Composable
fun IndicesDisplayError(){

    val con = LocalContext.current

    Column(
        modifier = Modifier
            .padding(0.dp, MySizes.VERTICAL_CONTENT_PADDING_SMALL)
            .fillMaxWidth()
            .fillMaxHeight(.15f),
    ) {

        Text(
            text = "World Indices",
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
            color = MaterialTheme.colorScheme.onBackground
        )

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
}