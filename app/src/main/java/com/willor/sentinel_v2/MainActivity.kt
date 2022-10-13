package com.willor.sentinel_v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.DestinationsNavHost
import com.willor.sentinel_v2.presentation.home.NavGraph
import com.willor.sentinel_v2.presentation.home.NavGraphs
import com.willor.sentinel_v2.presentation.home.NavGraphs.root
import com.willor.sentinel_v2.ui.theme.BaseComposeDIEzNavTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseComposeDIEzNavTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Create a Composable, set with @Destination(start = true)
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }
}

