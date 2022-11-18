package com.willor.sentinel_v2.presentation.common

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.willor.lib_data.domain.dataobjs.DataState
import com.willor.sentinel_v2.presentation.dashboard.dash_components.DashboardUiState


@Composable
fun SentinelWatchlistLazyrow(
    dashUiStateProvider: () -> DashboardUiState,
    onRemoveIconClicked: (ticker: String) -> Unit,
    onCardClicked: (ticker: String) -> Unit,
) {

    Log.d("DASHBOARD", "SentinelWatchlistComposed: ")

    var tickerCards = listOf<String>()

    when (dashUiStateProvider().userPrefs) {

        is DataState.Success -> {
            tickerCards =
                (dashUiStateProvider().userPrefs as DataState.Success).data.sentinelWatchlist
        }
        else -> {
            // Nothing is needed, Else block is here to follow Kotlin guidelines
        }
    }

    SentinelWatchlistContent(tickerCards, onRemoveIconClicked, onCardClicked)
}


@Composable
private fun SentinelWatchlistContent(
    tickerCards: List<String>,
    onRemoveIconClicked: (ticker: String) -> Unit,
    onCardClicked: (ticker: String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
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


@Composable
private fun TickerCardWithX(
    ticker: String,
    onRemoveIconClicked: (ticker: String) -> Unit,
    onCardClicked: (ticker: String) -> Unit
) {
    Card(
        modifier = Modifier
            .wrapContentSize()
            .clickable {
                onCardClicked(ticker)
            }
    ) {
        Box(
            modifier = Modifier
                .height(35.dp)
                .width(60.dp)
                .background(MaterialTheme.colorScheme.secondary),
        ) {
            Text(
                modifier = Modifier.align(Alignment.TopCenter),
                text = ticker,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondary,
            )
            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(18.dp, 18.dp),
                onClick = {
                    onRemoveIconClicked(ticker)
                }
            ) {
                Icon(
                    Icons.Filled.RemoveCircle,
                    "remove-ticker",
                    tint = Color.Red
                )
            }
        }
    }
}


