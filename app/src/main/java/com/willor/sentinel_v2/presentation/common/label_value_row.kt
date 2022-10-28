package com.willor.sentinel_v2.presentation.common

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun LabelValueRow(
    label: String,
    value: String,
    labelSuperScript: String = "",
    valueColor: Color? = null
){

    Log.d("THEME_TEST", "Dark Mode: ${isSystemInDarkTheme()}")
    Log.d("THEME_TEST", "Secondary Color: ${MaterialTheme.colorScheme.secondary}")
    Log.d("THEME_TEST", "OnSecondary Color: ${MaterialTheme.colorScheme.onSecondary}")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        LabelText(label = label, labelSuperScript = labelSuperScript)
        ValueText(value = value, valueColor)
    }
}


@Composable
private fun LabelText(
    label: String,
    labelSuperScript: String
){

    Row(
        modifier = Modifier.wrapContentSize()
    ){
        Text(
            text = label,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Bold,
            fontFamily = MaterialTheme.typography.titleSmall.fontFamily,
            color = MaterialTheme.colorScheme.onSecondary,
        )

        Text(
            text = labelSuperScript,
            fontSize = 8.sp,
            fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
            fontFamily = MaterialTheme.typography.titleSmall.fontFamily,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.align(Alignment.Top)
        )
    }


}



@Composable
private fun ValueText(
    value: String,
    color: Color? = null
){

    Text(
        text = value,
        fontSize = MaterialTheme.typography.titleSmall.fontSize,
        fontWeight = FontWeight.Bold,
        fontFamily = MaterialTheme.typography.titleSmall.fontFamily,
        color = color ?: MaterialTheme.colorScheme.onSecondary
    )
}




















