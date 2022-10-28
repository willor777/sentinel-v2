package com.willor.sentinel_v2.presentation.common

import android.util.Log
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.willor.sentinel_v2.ui.theme.DarkSelectedOption
import com.willor.sentinel_v2.ui.theme.LightSelectedOption
import com.willor.sentinel_v2.ui.theme.MySizes
import kotlinx.coroutines.launch


// TODO This file is currently unused as of 10-26-2022. Delete if unsued for too long.
@Composable
fun OptionsCarousel(
    options: List<String>,
    onOptionClick: (String) -> Unit
) {

    val lazyListState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    Log.d("TESTING", lazyListState.firstVisibleItemIndex.toString())

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        state = lazyListState,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        items(options.lastIndex) { itemIndex ->
            OptionItem(
                option = options[itemIndex],
                onOptionClick = {
                    onOptionClick(it)
                    coroutineScope.launch {
                        lazyListState.animateScrollToItem(itemIndex)
                    }
                },
                lazyListState = lazyListState,
                itemIndex = itemIndex
            )
        }
    }
}


@Composable
private fun OptionItem(
    option: String,
    onOptionClick: (String) -> Unit,
    lazyListState: LazyListState,
    itemIndex: Int
) {

    // TODO Animate a change in font size as well

    val isFirstItemVisible by derivedStateOf {
        itemIndex == lazyListState.firstVisibleItemIndex + 1
    }

    val ele by animateIntAsState(
        if (isFirstItemVisible) 10 else 3
    )

    val cardSize by animateIntAsState(
        if (isFirstItemVisible) 40 else 25
    )

    val fontSize by animateIntAsState(
        if (isFirstItemVisible) 16 else 12
    )

    val textColor = if (isFirstItemVisible && isSystemInDarkTheme()) {
        DarkSelectedOption
    } else if (isFirstItemVisible && !isSystemInDarkTheme()) {
        LightSelectedOption
    } else {
        MaterialTheme.colorScheme.onSecondary
    }

    Column(
        modifier = Modifier.clickable {
            onOptionClick(option)
        }
    ) {

        Card(
            modifier = Modifier
//                .background(MaterialTheme.colorScheme.secondary)
                .padding(
                    3.dp,
                    3.dp
                )
                .wrapContentWidth()
                .height(cardSize.dp),
            elevation = ele.dp,
        ) {
            Column(
                modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = option,
//                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontSize = fontSize.sp,
                    fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
                    fontFamily = MaterialTheme.typography.titleSmall.fontFamily,
                    color = textColor,
                    modifier = Modifier.padding(
                        start = MySizes.HORIZONTAL_CONTENT_PADDING_MEDIUM,
                        end = MySizes.HORIZONTAL_CONTENT_PADDING_MEDIUM,
                        top = MySizes.VERTICAL_CONTENT_PADDING_MEDIUM,
                        bottom = 0.dp
                    )
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                        .height(3.dp)
                )
            }
        }
    }
}

