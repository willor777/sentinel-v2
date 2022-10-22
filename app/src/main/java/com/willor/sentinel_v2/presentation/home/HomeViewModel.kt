package com.willor.sentinel_v2.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willor.lib_data.data.local.prefs.UserPreferences
import com.willor.lib_data.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState get() = _uiState


    init {
        Log.i(TAG, "${HomeViewModel::class.simpleName} Initialized.")
    }


    private fun loadDefaultWatchlist() {
        viewModelScope.launch(Dispatchers.IO) {

            // Wait for user prefs to be loaded
            while (this.isActive && _uiState.value.userPrefs == null) {
                delay(500)
            }

            // Load default watchlist
            loadWatchlist(
                _uiState.value.userPrefs!!.lastPopularWatchlistSelected
            )

            Log.i(TAG, "Default Watchlist Loaded: ${_uiState.value.popularWatchlistDisplayed}")
        }
    }

    /**
     * Collects user preferences flow from repo/datastore
     */
    private fun loadUserPrefs() {

        viewModelScope.launch(Dispatchers.IO) {
            useCases.getUserPreferencesUsecase().collectLatest {
                _uiState.value = _uiState.value.copy(
                    userPrefs = it
                )
                Log.i(TAG, "UserPrefs Loaded: $it")
            }
        }
    }


    /**
     * Updates the local copy of userPrefs, then updates the data store
     */
    private fun updateUserPrefs(userPrefs: UserPreferences) {
        viewModelScope.launch(Dispatchers.IO) {

            // Update local copy
            _uiState.value = _uiState.value.copy(
                userPrefs = userPrefs
            )

            // Update datastore
            useCases.saveUserPreferencesUsecase(userPrefs)

            Log.i(TAG, "UserPrefs Updated: $userPrefs")

        }
    }


    /**
     * Loads futures data from Repo. Will be called on a loop using Launched Effect to refresh.
     */
    fun loadMajorFuturesData() {

        viewModelScope.launch(Dispatchers.IO) {

            useCases.getMajorFuturesUsecase().collectLatest {

                _uiState.value = _uiState.value.copy(
                    majorFutures = it
                )

                Log.i(TAG, "MajorFutures Loaded: $it")
            }
        }
    }


    /**
     * Loads indices data from Repo. Will be called on a loop using Launched Effect to refresh.
     */


    /**
     * Loads the PopularWatchlistOptions from Repo. When Success -> loadWatchlist() is called
     * with the first watchlist in the options list.
     */
    fun loadPopularWatchlistOptions() {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getPopularWatchlistOptionsUsecase().collectLatest {

                _uiState.value = _uiState.value.copy(
                    popularWatchlistOptions = it
                )

                Log.i(TAG, "PopularWatchlistOptions Loaded: $it")
            }
        }
    }


    /**
     * Loads Popular watchlist to be displayed and sets UserPref.defaultWatchlist as wlName
     */
    fun loadWatchlist(wlName: String) {
        viewModelScope.launch(Dispatchers.IO) {

            // Set wlName as default in prefs
            if (_uiState.value.userPrefs != null) {
                val prefs = _uiState.value.userPrefs
                prefs!!.lastPopularWatchlistSelected = wlName
                updateUserPrefs(prefs)
                Log.i(TAG, "UserPrefs lastPopularWatchlistSelected changed to: $wlName")

            }

            // Load popular watchlist
            useCases.getPopularWatchlistUsecase(wlName).collectLatest {
                _uiState.value = _uiState.value.copy(
                    popularWatchlistDisplayed = it
                )
                Log.i(TAG, "PopularWatchlist Loaded: $it")
            }

        }
    }


    /**
     * Handles Events that occur with the UI
     */
    fun handleEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.InitialLoad -> {
                // Load user prefs
                loadUserPrefs()

                // Load major futures
                loadMajorFuturesData()

                // Load Wl Options
                loadPopularWatchlistOptions()

                // Load Default Wl
                loadDefaultWatchlist()
            }

            else -> {
                Log.i(TAG, "Unknown Event Type called: $event")
            }
        }
        Log.i(TAG, "Home Ui Event triggered. Event: $event")
    }


    companion object {
        const val TAG = "HOME_VIEW_MODEL"
    }
}
