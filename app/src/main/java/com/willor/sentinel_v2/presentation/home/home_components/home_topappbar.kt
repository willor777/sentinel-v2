package com.willor.sentinel_v2.presentation.home.home_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp


@Composable
fun HomeTopAppBar(
    onHomeIconClicked: () -> Unit,
    onSettingsIconClicked: () -> Unit,
){

    SmallTopAppBar(
        title = {
            Text(
                "Sentinel Dashboard",
                fontSize = 14.sp
            )
        },
        navigationIcon = {
            IconButton(onClick = { onHomeIconClicked() }) {
                Icon(Icons.Filled.Home, "go-home-icon", tint = Color.White)
            }
        },
        actions = {
          IconButton(onClick = { onSettingsIconClicked() }) {
              Icon(Icons.Filled.Settings, "go-to-settings-icon", tint = Color.White)
          }

            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Menu, "go-to-Menu", tint = Color.White)
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Black,
            titleContentColor = Color.White
        )
    )

}