package com.willor.sentinel_v2.presentation.dashboard.dash_components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.willor.lib_data.domain.dataobjs.DataState
import com.willor.sentinel_v2.presentation.common.TickerCardLazyRow
import com.willor.sentinel_v2.presentation.dashboard.DashboardUiState
import com.willor.sentinel_v2.ui.theme.MySizes


@Composable
fun DashCollapsableTopBar(
    dashUiStateProvider: () -> State<DashboardUiState>,
    dashScrollState: () -> ScrollState,
    onNavIconClick: () -> Unit,
    onSettingsIconClick: () -> Unit,
    onTickerCardClicked: (ticker: String) -> Unit
){

//    val scrollState = remember{ dashScrollState() }

    val dstate by derivedStateOf {
        dashScrollState().value > 224
    }

    val curHeight by animateDpAsState(
        if (dstate) 112.dp else 224.dp
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(curHeight)
            .background(MaterialTheme.colorScheme.primary)
            .padding(
                top = MySizes.VERTICAL_EDGE_PADDING,
                bottom = MySizes.VERTICAL_CONTENT_PADDING_SMALL,
                start = MySizes.HORIZONTAL_EDGE_PADDING,
                end = MySizes.HORIZONTAL_EDGE_PADDING
            )
    ){
        
        ContentCrossfadeController(
            curHeight = curHeight,
            dashUiStateProvider = dashUiStateProvider,
            onTickerCardClicked = onTickerCardClicked,
            onNavIconClick = onNavIconClick,
            onSettingsIconClick = onSettingsIconClick
            )
    }
}


@Composable
private fun ContentCrossfadeController(
    curHeight: Dp,
    dashUiStateProvider: () -> State<DashboardUiState>,
    onTickerCardClicked: (ticker: String) -> Unit,
    onNavIconClick: () -> Unit,
    onSettingsIconClick: () -> Unit
){
    Column(
        modifier = Modifier.wrapContentSize()
    ){
        BaseTopBar(
            onNavIconClick = onNavIconClick,
            onSettingsIconClick = onSettingsIconClick
        )

        // Cross fade was making it janky so i turned it off
//        Crossfade(targetState = curHeight) {
            when(curHeight){
                224.dp -> {
                    FullSizeTopBar(
                        dashUiStateProvider, onTickerCardClicked, onNavIconClick, onSettingsIconClick
                    )
                }
                112.dp -> {
                    SmallSizeTopBar(
                        dashUiStateProvider, onTickerCardClicked, onNavIconClick, onSettingsIconClick
                    )
                }
            }
//        }
    }


}


@Composable
private fun FullSizeTopBar(
    dashUiStateProvider: () -> State<DashboardUiState>,
    onTickerCardClicked: (ticker: String) -> Unit,
    onNavIconClick: () -> Unit,
    onSettingsIconClick: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(168.dp)
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Top
    ){

        ExpandedBarContent(
            dashUiStateProvider = dashUiStateProvider,
            onTickerCardClicked = onTickerCardClicked
        )

    }
}


@Composable
private fun SmallSizeTopBar(
    dashUiStateProvider: () -> State<DashboardUiState>,
    onTickerCardClicked: (ticker: String) -> Unit,
    onNavIconClick: () -> Unit,
    onSettingsIconClick: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        CollapsedBarContent(
            dashUiStateProvider = dashUiStateProvider,
            onTickerCardClicked = onTickerCardClicked
        )

    }
}


@Composable
private fun CollapsedBarContent(
    dashUiStateProvider: () -> State<DashboardUiState>,
    onTickerCardClicked: (ticker: String) -> Unit
){
    val indexData = dashUiStateProvider().value.majorIndices

    when (indexData){
        is DataState.Success -> {
            val tickList = mutableListOf<String>()
            val gdList = mutableListOf<Double>()
            val gpList = mutableListOf<Double>()
            indexData.data!!.data.forEach {
                tickList.add(it.name); gdList.add(it.changeDollar); gpList.add(it.changePercent)
            }

            // Container Row
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(84.dp),

                ){

                TickerCardLazyRow(
                    tickerList = tickList,
                    gainDollarList = gdList,
                    gainPctList = gpList,
                    onItemClicked = onTickerCardClicked
                )
            }
        }
        else -> {
            // TODO Don't need anything here, except maybe for error
        }
    }
}


/**
 * Content for the expanded top app bard includes Futures + Major Indices lazy row.
 */
@Composable
private fun ExpandedBarContent(
    dashUiStateProvider: () -> State<DashboardUiState>,
    onTickerCardClicked: (ticker: String) -> Unit
){

    val futuresData = dashUiStateProvider().value.majorFutures
    val indexData = dashUiStateProvider().value.majorIndices
    when (futuresData){
        is DataState.Success -> {

            val tickList = mutableListOf<String>()
            val gdList = mutableListOf<Double>()
            val gpList = mutableListOf<Double>()
            futuresData.data!!.data.forEach {
                tickList.add(it.name); gdList.add(it.changeDollar); gpList.add(it.changePercent)
            }

            // Ticker Card Container Row
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(84.dp),
            ){
                Text(
                    text = "Futures",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    fontFamily = MaterialTheme.typography.titleMedium.fontFamily,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(5.dp))
                TickerCardLazyRow(
                    tickerList = tickList,
                    gainDollarList = gdList,
                    gainPctList = gpList,
                    onItemClicked = onTickerCardClicked
                )
            }

        }

        else -> {
            // TODO Don't need anything here, except maybe for error
        }
    }

    when (indexData){

        is DataState.Success -> {

            val tickList = mutableListOf<String>()
            val gdList = mutableListOf<Double>()
            val gpList = mutableListOf<Double>()
            indexData.data!!.data.forEach {
                tickList.add(it.name); gdList.add(it.changeDollar); gpList.add(it.changePercent)
            }
            // Container Row
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(84.dp),

            ){
                Text(
                    text = "World Indices",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    fontFamily = MaterialTheme.typography.titleMedium.fontFamily,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(5.dp))
                TickerCardLazyRow(
                    tickerList = tickList,
                    gainDollarList = gdList,
                    gainPctList = gpList,
                    onItemClicked = onTickerCardClicked
                )
            }


        }

        else -> {
            // TODO Don't need anything here, except maybe for error
        }
    }

}


/**
 * 56.dp height base top app bar containing Icons and Screen name.
 */
@Composable
private fun BaseTopBar(
    onNavIconClick: () -> Unit,
    onSettingsIconClick: () -> Unit,
){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        // Open Drawer Icon...
        IconButton(
            onClick = onNavIconClick
        ){
            Icon(Icons.Default.MenuOpen, "", tint = Color.White)
        }

        // Screen Header
        Text(
            text = "Dashboard",
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
            fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
            fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
            color = Color.White
        )

        // Settings Icon
        IconButton(
            onClick = onSettingsIconClick
        ){
            Icon(Icons.Filled.Settings, "settings-icon", tint = Color.White)
        }
    }

}









