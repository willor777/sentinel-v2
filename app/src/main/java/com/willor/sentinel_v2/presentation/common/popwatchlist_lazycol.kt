package com.willor.sentinel_v2.presentation.common

import android.graphics.Color
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.willor.lib_data.domain.dataobjs.responses.popular_wl_resp.Ticker
import com.willor.sentinel_v2.ui.theme.GainGreen
import com.willor.sentinel_v2.ui.theme.LossRed
import com.willor.sentinel_v2.ui.theme.MySizes
import com.willor.sentinel_v2.utils.calculateRatio
import com.willor.sentinel_v2.utils.determineGainLossColor
import com.willor.sentinel_v2.utils.formatChangeDollarAndChangePct
import com.willor.sentinel_v2.utils.formatDoubleToTwoDecimalPlaceString


@Composable
fun PopWatchlistLazyCol(
    wlData: List<Ticker>,
    onTickerClick: (String) -> Unit
) {


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

    val volumeRatio = calculateRatio(ticker.volume.toDouble(), ticker.volumeThirtyDayAvg.toDouble())
    val volRatioColor = if (volumeRatio > 1){
        GainGreen
    }else{
        LossRed
    }

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
                .padding(
                    MySizes.HORIZONTAL_CONTENT_PADDING_MEDIUM,
                    MySizes.VERTICAL_CONTENT_PADDING_MEDIUM
                )
        ) {

            LabelValueRow(
                label = ticker.ticker,
                value = formatChangeDollarAndChangePct(ticker.changeDollar, ticker.changePercent),
                valueColor = determineGainLossColor(ticker.changeDollar),
                labelSuperScript = "Stock"
            )

            LabelValueRow(
                label = "Volume",
                value = ticker.volume.toString(),
                labelSuperScript = "Today"
            )

            LabelValueRow(
                label = "Avg. Volume",
                value = ticker.volumeThirtyDayAvg.toString(),
                labelSuperScript = "30d"
            )

            LabelValueRow(
                label = "Vol / Avg. Vol Ratio",
                value = formatDoubleToTwoDecimalPlaceString(volumeRatio),
                valueColor = volRatioColor
            )
        }

    }
}
