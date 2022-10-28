package com.willor.sentinel_v2.presentation.common

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.willor.sentinel_v2.ui.theme.DarkSelectedOption
import com.willor.sentinel_v2.ui.theme.LightSelectedOption
import com.willor.sentinel_v2.ui.theme.MySizes
import kotlin.math.exp


@Composable
fun DropdownOptionSelector(
    optionsList: List<String>,
    curSelection: String,
    onItemClick: (String) -> Unit,
){


    // Reorder list so that it has currentSelection as first value
    var data by remember {
        mutableStateOf(reOrderList(curSelection, optionsList))
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    Log.d("TESTING", "OPTIONS LIST: $optionsList")

    Column(
        modifier = Modifier
            .animateContentSize()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        CurrentSelectionCard(title = curSelection, onClick = {
            expanded = true
        })

        DropdownOptionsLazyCol(data, curSelection, expanded, onItemClick = {
            expanded = false
            onItemClick(it)
        })
    }
}

@Composable
private fun DropdownOptionsLazyCol(
    data: List<String>,
    curSelection: String,
    expanded: Boolean,
    onItemClick: (String) -> Unit
) {
    if (expanded){
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            items(data.lastIndex) { itemIndex ->
                DropDownRowItem(
                    title = data[itemIndex],
                    isSelected = curSelection == data[itemIndex],
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
    isSelected: Boolean,
    onItemClick: (String) -> Unit
){
    // Color = onSecondary if not selected, else SelectedOption color
    val color = if (!isSelected) {
        MaterialTheme.colorScheme.onSecondary
    }

    else {
        if (isSystemInDarkTheme()){
            DarkSelectedOption
        }else{
            LightSelectedOption
        }
    }


    Row(
        modifier = Modifier
            .clickable { onItemClick(title) }
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .wrapContentHeight()
            .padding(MySizes.HORIZONTAL_CONTENT_PADDING_MEDIUM)
            .border(Dp.Hairline, Color.Black),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){

        Text(
            text = title,
            fontSize = MaterialTheme.typography.titleSmall.fontSize,
            fontFamily = MaterialTheme.typography.titleSmall.fontFamily,
            fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
            color = color,
            modifier = Modifier.padding(MySizes.HORIZONTAL_CONTENT_PADDING_LARGE)
        )
    }
}


@Composable
private fun CurrentSelectionCard(
    title: String,
    onClick: () -> Unit,
){
    Card(
        elevation = MySizes.CARD_ELEVATION,
        modifier = Modifier.clickable {
            onClick()
        }
    ){
        Text(
            text = title,
            fontSize = MaterialTheme.typography.titleSmall.fontSize,
            fontFamily = MaterialTheme.typography.titleSmall.fontFamily,
            fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(MySizes.HORIZONTAL_CONTENT_PADDING_LARGE)
        )
    }
}


private fun reOrderList(curSelection: String, listOfOptions: List<String>): List<String>{
    val newList = listOfOptions.drop(listOfOptions.indexOf(curSelection)).toMutableList()


    listOfOptions.also{
        newList.add(curSelection)
        newList += it
    }
    return newList
}












