package com.willor.sentinel_v2.presentation.common

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.willor.sentinel_v2.presentation.dashboard.DashboardUiState


@Composable
fun SentinelWatchlistLazyrow(
    dashUiStateProvider: () -> State<DashboardUiState>,
    onRemoveIconClicked: (ticker: String) -> Unit,
    onCardClicked: (ticker: String) -> Unit,
){

    Log.d("HOME", "SentinelWatchlistComposed: ")

    val uiState = dashUiStateProvider().value


    val tickerCards = uiState.userPrefs?.sentinelWatchlist

    SentinelWatchlistContent(tickerCards, onRemoveIconClicked, onCardClicked)

}

@Composable
private fun SentinelWatchlistContent(
    tickerCards: List<String>?,
    onRemoveIconClicked: (ticker: String) -> Unit,
    onCardClicked: (ticker: String) -> Unit
) {
    if (!tickerCards.isNullOrEmpty()) {
        LazyRow(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(tickerCards.size) { itemIndex ->
                TickerCardWithX(
                    ticker = tickerCards[itemIndex],
                    onRemoveIconClicked = onRemoveIconClicked,
                    onCardClicked = onCardClicked
                )
            }
        }
    }
}

@Composable
private fun TickerCardWithX(
    ticker: String,
    onRemoveIconClicked: (ticker: String) -> Unit,
    onCardClicked: (ticker: String) -> Unit
){
        Card(){
            Box(){
                Text(
                    text = ticker,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    color = MaterialTheme.colorScheme.onSecondary,
                )
                IconButton(onClick = {

                }
                ) {
//                    Icon(Icons.Filled)
                }
            }
        }
}


