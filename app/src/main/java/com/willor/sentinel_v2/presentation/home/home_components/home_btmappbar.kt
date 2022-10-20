package com.willor.sentinel_v2.presentation.home.home_components

import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun HomeBottomAppBar(
    onSearchIconClicked: () -> Unit,
    onWatchlistClicked: () -> Unit,
    onTriggerHistoryClicked: () -> Unit,
    onStatSentinelClicked: () -> Unit
){

    BottomAppBar(
        backgroundColor = Color.Black,
    ) {

        // Quote Search Button
        BottomNavigationItem(
            selected = false,
            label = {
                Text("Quotes", fontSize = 9.sp, color = Color.White)
            },
            alwaysShowLabel = true,
            onClick = {
                onSearchIconClicked()
            },
            icon = {
                Icon(
                    Icons.Filled.Search,
                    "quotes-icon",
                    tint = Color.White
                )
            }
        )

        BottomNavigationItem(
            selected = false,
            label = {
                Text("Watchlist", fontSize = 9.sp, color = Color.White)
            },
            alwaysShowLabel = true,
            onClick = {
                onWatchlistClicked()
            },
            icon = {
                Icon(
                    Icons.Filled.Edit,
                    "edit-watchlist-icon",
                    tint = Color.White
                )
            }
        )

        BottomNavigationItem(
            selected = false,
            label = {
                Text("Triggers", fontSize = 9.sp, color = Color.White)
            },
            alwaysShowLabel = true,
            onClick = {
                onTriggerHistoryClicked()
            },
            icon = {
                Icon(
                    Icons.Filled.Schedule,
                    "trigger-history-icon",
                    tint = Color.White)
            }
        )

        BottomNavigationItem(
            selected = false,
            label = {
                Text("Sentinel", fontSize = 9.sp, color = Color.White)
            },
            alwaysShowLabel = true,
            onClick = {
                onStatSentinelClicked()
            },
            icon = {
                Icon(
                    Icons.Filled.Visibility,
                    "start-sentinel-icon",
                    tint = Color.White
                )
            }
        )
    }

}