package com.willor.sentinel_v2.utils

import androidx.compose.ui.graphics.Color
import com.willor.sentinel_v2.ui.theme.GainGreen
import com.willor.sentinel_v2.ui.theme.LossRed


fun formatChangeDollarAndChangePct(
    changeDollar: Double,
    changePct: Double
): String{

    val plusMinus = if (changeDollar > 0){
        "+"
    }else{
        "-"
    }

    // Example "+ $1.20 ( %0.01 )"
    return "$plusMinus $${formatDoubleToTwoDecimalPlaceString(changeDollar)}" +
            " ( %${formatDoubleToTwoDecimalPlaceString(changePct)} )"

}


fun formatDoubleToTwoDecimalPlaceString(d: Double): String{
    val strDouble = d.toString().split(".")

    val whole = strDouble[0]

    val dec = if (strDouble[1].length > 2){
        strDouble[1].substring(0, 2)
    }else{
        strDouble[1].padEnd(2, '0')
    }
    return "$whole.$dec"
}


fun determineGainLossColor(d: Double): Color {
    return if (d > 0){
        GainGreen
    }else{
        LossRed
    }
}


fun calculateRatio(curValue: Double, avgValue: Double): Double{
    return curValue / avgValue
}


// TODO Doesn't work
fun isStockMarketOpen(): Boolean {
    return false
}
