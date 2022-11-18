package com.willor.sentinel_v2.presentation.quote.quote_components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.willor.lib_data.domain.dataobjs.DataState
import com.willor.lib_data.domain.dataobjs.responses.etf_quote_resp.EtfQuote
import com.willor.lib_data.domain.dataobjs.responses.stock_quote_resp.StockQuote
import com.willor.sentinel_v2.presentation.common.LabelValueRow
import com.willor.sentinel_v2.ui.theme.MySizes
import com.willor.sentinel_v2.utils.determineGainLossColor
import com.willor.sentinel_v2.utils.formatChangeDollarAndChangePct
import com.willor.sentinel_v2.utils.formatDoubleToTwoDecimalPlaceString


@Composable
fun QuoteMainContent(
    quoteUiStateProvider: () -> QuoteUiState
) {

    val uiState = quoteUiStateProvider()

    val stockQuote = uiState.stockQuote
    val etfQuote = uiState.etfQuote

    when {
        stockQuote is DataState.Success -> {
            StockQuoteContent(stockQuote = stockQuote.data)
        }
        etfQuote is DataState.Success -> {
            EtfQuoteContent(etfQuote = etfQuote.data)
        }
        else -> {
            ErrorAnimation()
        }
    }

}


@Composable
private fun StockQuoteContent(
    stockQuote: StockQuote?
) {

    stockQuote.let { quote ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            Spacer(modifier = Modifier.height(MySizes.VERTICAL_CONTENT_PADDING_LARGE))

            QuoteHeader(ticker = quote!!.data.ticker)           // Header

            Spacer(modifier = Modifier.height(5.dp))

            LabelValueRow(                                      // Last Price
                label = "Last Price",
                value = formatDoubleToTwoDecimalPlaceString(
                    determineLastPrice(quote.data.lastPriceRegMarket, quote.data.prepostPrice)
                )
            )

            LabelValueRow(
                label = "Change",
                value = formatChangeDollarAndChangePct(
                    changeDollar = determineChangePrepostOrReg(
                        quote.data.changeDollarRegMarket, quote.data.prepostChangeDollar
                    ),
                    changePct = determineChangePrepostOrReg(
                        quote.data.changePctRegMarket, quote.data.prepostChangePct
                    )
                ),
                valueColor = determineGainLossColor(determineChangePrepostOrReg(
                    quote.data.changePctRegMarket, quote.data.prepostChangePct
                ))
            )


        }
    }
}


@Composable
private fun EtfQuoteContent(
    etfQuote: EtfQuote?
) {

    etfQuote.let { quote ->

        QuoteHeader(ticker = quote!!.data.ticker)

        Spacer(modifier = Modifier.height(5.dp))

        LabelValueRow(
            label = "Last Price",
            value = formatDoubleToTwoDecimalPlaceString(
                determineLastPrice(quote.data.lastPriceRegMarket, quote.data.prepostPrice)
            ),
        )

    }

}


@Composable
private fun QuoteHeader(
    ticker: String
) {
    Text(
        text = "$ticker Base Quote...",
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
        fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
        fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
        fontWeight = FontWeight.Bold
    )
}


@Composable
private fun ErrorAnimation() {

}


private fun determineLastPrice(regMarket: Double, prepost: Double): Double{
    return if (prepost != 0.0) prepost else regMarket
}


private fun determineChangePrepostOrReg(
    regMarketChangeDollar: Double,
    prepostChangeDollar: Double
): Double{
    return if (prepostChangeDollar != 0.0) prepostChangeDollar else regMarketChangeDollar
}










