package com.willor.sentinel_v2.presentation.home.home_components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.willor.lib_data.domain.dataobjs.responses.major_futures_resp.Future
import com.willor.lib_data.domain.dataobjs.responses.major_futures_resp.MajorFutures
import com.willor.sentinel_v2.presentation.home.HOME_TAG
import com.willor.sentinel_v2.ui.theme.MySizes
import com.willor.sentinel_v2.utils.determineGainLossColor
import com.willor.sentinel_v2.utils.formatChangeDollarAndChangePct


@Composable
fun HomeFuturesDisplayLazyRow(
    majorFutures: MajorFutures,
    onItemClicked: (ticker: String) -> Unit


){
    Log.i(HOME_TAG, "HomeFuturesDisplayLazyRow Composed.")

    Column(
        modifier = Modifier
            .padding(0.dp, MySizes.VERTICAL_CONTENT_PADDING_SMALL)
            .fillMaxWidth()
            .fillMaxHeight(.15f),
    ){
        Text(
            text = "Futures",
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
            color = MaterialTheme.colorScheme.onBackground
        )

        LazyRow(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(MySizes.HORIZONTAL_CONTENT_PADDING_MEDIUM, 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(majorFutures.data.lastIndex){ itemIndex ->
                FutureItemDisplayCard(
                    future = majorFutures.data[itemIndex],
                    onCardClicked = {
                        onItemClicked(it)
                    }
                )
            }
        }

    }





}



@Composable
fun FutureItemDisplayCard(
    future: Future,
    onCardClicked: (ticker: String) -> Unit
){
    
    val glColor = determineGainLossColor(future.changeDollar)
    
    Card(
        modifier = Modifier.wrapContentSize()
            .clickable {
                onCardClicked(future.ticker)
            },
        shape = RoundedCornerShape(MySizes.CARD_ROUNDED_CORNER),
        elevation = MySizes.CARD_ELEVATION,
//        contentColor = MaterialTheme.colorScheme.onSecondary,
//        backgroundColor = MaterialTheme.colorScheme.secondary
    ){
        Column(
            modifier = Modifier.fillMaxHeight().fillMaxWidth(.45f)
                .background(MaterialTheme.colorScheme.secondary),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){

            // Future Name
            Text(
                text = future.name,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
                fontFamily = MaterialTheme.typography.titleSmall.fontFamily,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.padding(
                    MySizes.HORIZONTAL_CONTENT_PADDING_SMALL,
                    MySizes.VERTICAL_CONTENT_PADDING_SMALL
                )
            )

            // Future Change
            Text(
                text = formatChangeDollarAndChangePct(
                    future.changeDollar,
                    future.changePercent
                ),
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                fontFamily = MaterialTheme.typography.bodySmall.fontFamily,
                color = glColor,
                modifier = Modifier.padding(
                    MySizes.HORIZONTAL_CONTENT_PADDING_SMALL,
                    MySizes.VERTICAL_CONTENT_PADDING_SMALL
                )
            )
        }

    }
}












