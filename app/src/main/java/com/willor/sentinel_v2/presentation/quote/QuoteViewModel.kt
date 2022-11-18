package com.willor.sentinel_v2.presentation.quote

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willor.lib_data.domain.dataobjs.DataState
import com.willor.lib_data.domain.usecases.UseCases
import com.willor.lib_data.utils.TickerSymbolLoader
import com.willor.sentinel_v2.presentation.quote.quote_components.QuoteUiEvent
import com.willor.sentinel_v2.presentation.quote.quote_components.QuoteUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val usecases: UseCases
) : ViewModel() {

    private val tag: String = QuoteViewModel::class.java.simpleName

    private val _uiState: MutableStateFlow<QuoteUiState> = MutableStateFlow(QuoteUiState())
    val uiState: StateFlow<QuoteUiState> get() = _uiState.asStateFlow()

    private var bigListOfCompanies: List<List<String>>? = null

    private var curQuoteType = "UNKNOWN"

    fun handleUiEvent(event: QuoteUiEvent) {

        when (event) {
            is QuoteUiEvent.InitialLoad -> {
                updateCurrentTicker(event.ticker)
                loadQuote()
            }

            is QuoteUiEvent.SearchTextUpdated -> {
                updateSearchText(event.txt)
                updateSearchResults()
            }

            else -> {

            }
        }
    }


    private fun loadQuote() {

        val ticker = _uiState.value.currentTicker

        // Shouldn't happen but just in case
        if (ticker.isEmpty()) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {

            when (curQuoteType) {
                "STOCK" -> {
                    loadStockQuote()
                    loadOptionsOverview()
                }
                "ETF" -> {
                    loadEtfQuote()
                    loadOptionsOverview()

                }
                "UNKNOWN" -> {
                    loadUnknownQuote()
                    loadOptionsOverview()
                }
            }
        }
    }


    private fun loadUnknownQuote() {
        viewModelScope.launch(Dispatchers.IO) {

            val ticker = _uiState.value.currentTicker
            var getEtfQuote = false

            // attempt to retreive a stock quote first
            usecases.getStockQuoteUsecase(ticker).collectLatest {
                when (it) {
                    is DataState.Success -> {
                        _uiState.value = _uiState.value.copy(
                            stockQuote = it
                        )
                        Log.d(tag, "loadUnknownQuote() -> StockQuote retrieved for $ticker")
                        curQuoteType = "STOCK"
                    }
                    is DataState.Error -> {
                        getEtfQuote = true
                    }
                    is DataState.Loading -> {

                    }
                }
            }

            // If stock quote fails retrieve etf quote
            if (getEtfQuote) {
                usecases.getEtfQuoteUsecase(ticker).collectLatest {
                    when (it) {
                        is DataState.Success -> {
                            _uiState.value = _uiState.value.copy(
                                etfQuote = it
                            )
                            Log.d(tag, "loadUnknownQuote() -> EtfQuote retrieved for $ticker")
                            curQuoteType = "ETF"
                        }
                        is DataState.Loading -> {

                        }
                        is DataState.Error -> {

                        }
                    }
                }
            }
        }
    }


    private fun loadStockQuote() {

        val ticker = _uiState.value.currentTicker

        viewModelScope.launch(Dispatchers.IO) {
            usecases.getStockQuoteUsecase(ticker).collectLatest {
                when (it) {
                    is DataState.Success -> {
                        _uiState.value = _uiState.value.copy(
                            stockQuote = it
                        )
                        Log.d(tag, "loadUnknownQuote() -> StockQuote retrieved for $ticker")
                        curQuoteType = "STOCK"
                    }
                    is DataState.Loading -> {

                    }
                    is DataState.Error -> {

                    }
                }
            }
        }
    }

    private fun loadEtfQuote() {
        val ticker = _uiState.value.currentTicker

        viewModelScope.launch(Dispatchers.IO) {
            usecases.getEtfQuoteUsecase(ticker).collectLatest {
                when (it) {
                    is DataState.Success -> {
                        _uiState.value = _uiState.value.copy(
                            etfQuote = it
                        )
                        Log.d(tag, "loadUnknownQuote() -> EtfQuote retrieved for $ticker")
                        curQuoteType = "ETF"
                    }
                    is DataState.Loading -> {

                    }
                    is DataState.Error -> {

                    }
                }
            }
        }
    }

    private fun loadOptionsOverview() {
        val ticker = _uiState.value.currentTicker

        viewModelScope.launch(Dispatchers.IO) {
            usecases.getOptionsOverviewUsecase(ticker).collectLatest {
                when (it) {
                    is DataState.Success -> {
                        _uiState.value = _uiState.value.copy(
                            optionsOverview = it
                        )
                    }
                    is DataState.Loading -> {

                    }
                    is DataState.Error -> {

                    }
                }
            }
        }
    }


    /**
     * Updates the QuotUiState currentTicker field, resets the stock, etf, options quotes
     */
    private fun updateCurrentTicker(ticker: String) {
        _uiState.value = _uiState.value.copy(
            currentTicker = ticker,
            stockQuote = DataState.Loading(),
            etfQuote = DataState.Loading(),
            optionsOverview = DataState.Loading()
        )

        // reset the quote meta data
        curQuoteType = "UNKNOWN"
    }


    /**
     * Updates the search text in the QuoteUiState object's state flow.
     * */
    private fun updateSearchText(usrSearchText: String) {
        _uiState.value = _uiState.value.copy(
            currentSearchText = usrSearchText
        )
    }


    /**
     * Checks the bigListOfCompanies for matching search, then updates the QuoteUiState with search results.
     */
    private fun updateSearchResults() {

        // Extract usr search text from _uiState
        val usrSearchText = _uiState.value.currentSearchText
        if (usrSearchText.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                currentSearchResults = listOf()
            )
            return
        }

        viewModelScope.launch(Dispatchers.IO) {

            // Load list if not already loaded
            if (bigListOfCompanies.isNullOrEmpty()) {
                bigListOfCompanies = TickerSymbolLoader.loadSymbols()
            }

            // Search list
            val firstChar = usrSearchText[0]
            val startIndex = TickerSymbolLoader.findStartingIndexOfChar(firstChar)
            val searchResults = mutableListOf<List<String>>()

            for (n in startIndex..bigListOfCompanies!!.lastIndex) {

                val (ticker, compName, type) = bigListOfCompanies!![n]

                // Check if into a new Char
                if (ticker[0] != firstChar) {
                    break
                }

                // Check if ticker is a match to usr text
                if (ticker.startsWith(usrSearchText)) {
                    searchResults.add(bigListOfCompanies!![n])
                }
            }

            _uiState.value = _uiState.value.copy(
                currentSearchResults = searchResults
            )

        }
    }

}