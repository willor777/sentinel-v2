package com.willor.sentinel_v2.presentation.common

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.willor.lib_data.domain.dataobjs.responses.popular_wl_resp.Ticker
import com.willor.sentinel_v2.ui.theme.MySizes
import com.willor.sentinel_v2.utils.determineGainLossColor
import com.willor.sentinel_v2.utils.formatChangeDollarAndChangePct


@Composable
fun PopWatchlistLazyCol(
    wlData: List<Ticker>,
    onTickerClick: (String) -> Unit
) {

    Log.d("LAZYCOL", "wlData: ${wlData}")

    // Might not be needed. Plan to use to scroll to top if user changes watchlist
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

            items(wlData.lastIndex) { itemIndex ->
                TickerCard(
                    ticker = wlData[itemIndex],
                    onSearchIconClick = onTickerClick
                )
            }
        }
    }
}


@Composable
fun TickerCard(
    ticker: Ticker,
    onSearchIconClick: (String) -> Unit
) {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    val size by animateDpAsState(
        if (!isExpanded) 100.dp else 250.dp
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(size)
            .clickable {
                isExpanded = !isExpanded
            },
        elevation = MySizes.CARD_ELEVATION
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            LabelValueRow(
                label = ticker.ticker,
                value = formatChangeDollarAndChangePct(ticker.changeDollar, ticker.changePercent),
                valueColor = determineGainLossColor(ticker.changeDollar),
                labelSuperScript = "Stock"
            )

//            Text(
//                text = ticker.ticker
//            )
//
//            Text(               // TODO Change dollar and pct can also be Prepost -- Fix that
//                text = formatChangeDollarAndChangePct(ticker.changeDollar, ticker.changePercent)
//            )

        }

    }
}












