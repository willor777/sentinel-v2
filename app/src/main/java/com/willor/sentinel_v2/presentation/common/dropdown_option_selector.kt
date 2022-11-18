package com.willor.sentinel_v2.presentation.common

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.willor.sentinel_v2.ui.theme.MySizes
import com.willor.sentinel_v2.utils.formatWatchlistNameForDisplay


@Composable
fun DropdownOptionSelector(
    optionsList: List<String>,
    curSelection: String,
    onItemClick: (String) -> Unit,
) {

    // Reorder list so that it has currentSelection as first value
    val data by remember {
        mutableStateOf(
            optionsList.filter { it != curSelection }.sorted()     // Remove cur selection from list
        )
    }

    var expanded by remember {
        mutableStateOf(false)
    }


    Column(
        modifier = Modifier
            .animateContentSize()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        CurrentSelectionCard(title = curSelection, onClick = {
            expanded = !expanded
        })

        DropdownOptionsLazyCol(data, expanded, onItemClick = {
            expanded = false
            onItemClick(it)
        })
    }
}

@Composable
private fun DropdownOptionsLazyCol(
    data: List<String>,
    expanded: Boolean,
    onItemClick: (String) -> Unit
) {
    if (expanded) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            items(data.lastIndex) { itemIndex ->
                DropDownRowItem(
                    title = data[itemIndex],
                    onItemClick = {
                        onItemClick(it)
                    }
                )
            }
        }
    }

}


@Composable
private fun DropDownRowItem(
    title: String,
    onItemClick: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .clickable { onItemClick(title) }
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .wrapContentHeight()
            .padding(MySizes.HORIZONTAL_CONTENT_PADDING_SMALL),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = formatWatchlistNameForDisplay(title),
            fontSize = MaterialTheme.typography.titleSmall.fontSize,
            fontFamily = MaterialTheme.typography.titleSmall.fontFamily,
            fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(MySizes.HORIZONTAL_CONTENT_PADDING_LARGE)
        )
    }
}


@Composable
private fun CurrentSelectionCard(
    title: String,
    onClick: () -> Unit,
) {
    Card(
        elevation = MySizes.CARD_ELEVATION,
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        Text(
            text = formatWatchlistNameForDisplay(title),
            fontSize = MaterialTheme.typography.titleSmall.fontSize,
            fontFamily = MaterialTheme.typography.titleSmall.fontFamily,
            fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(MySizes.HORIZONTAL_CONTENT_PADDING_LARGE)
        )
    }
}



