package com.willor.sentinel_v2.presentation.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willor.lib_data.data.local.prefs.UserPreferences
import com.willor.lib_data.domain.dataobjs.DataState
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
class DashboardViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    val tag: String = DashboardViewModel::class.java.simpleName

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState get() = _uiState

    private val _userPrefs: MutableStateFlow<UserPreferences?> = MutableStateFlow(null)
    val userPrefs get() = _userPrefs

    init {
        Log.i(tag, "Initialized.")
    }


    //  TODO -- inside
    private fun loadUserPrefs(){
        viewModelScope.launch(Dispatchers.IO){
            useCases.getUserPreferencesUsecase().collectLatest {
                _userPrefs.value = it

                // Shouldn't need this after re-work
                _uiState.value = _uiState.value.copy(
                    userPrefs = it
                )
            }
        }
    }


    private fun saveUserPrefs(usrPrefs: UserPreferences){
        viewModelScope.launch(Dispatchers.IO){
            useCases.saveUserPreferencesUsecase(usrPrefs)
        }
    }


    private fun addTickerToSentinelWatchlist(ticker: String){
        viewModelScope.launch(Dispatchers.IO){
            val curPrefs = _userPrefs.value!!
            val curWatchlist = curPrefs.sentinelWatchlist.toMutableList()

        }
    }

//    /**
//     * Adds a ticker to the user prefs list
//     */
//    private fun addTickerToSentinelWatchlist(ticker: String){
//        viewModelScope.launch(Dispatchers.IO){
//            val userPrefs = _uiState.value.userPrefs ?: return@launch
//            val curList = userPrefs.sentinelWatchlist.toMutableList()
//            curList.add(ticker)
//
//            userPrefs.sentinelWatchlist = curList.toList()
//
//            updateUserPrefs(userPrefs)
//            Log.i(tag, "Ticker added to user prefs watchlist: $ticker")
//        }
//    }
//
//
//    /**
//     * Waits for user prefs to load, then loads the last watchlist user selected.
//     */
//    private fun loadDefaultWatchlist() {
//        viewModelScope.launch(Dispatchers.IO) {
//
//            // Wait for user prefs to be loaded
//            while (this.isActive && _uiState.value.userPrefs == null) {
//                delay(500)
//            }
//
//            // Load default watchlist
//            loadWatchlist(
//                _uiState.value.userPrefs!!.lastPopularWatchlistSelected
//            )
//
//            Log.i(tag, "Default Watchlist Loaded: ${_uiState.value.popularWatchlistDisplayed}")
//        }
//    }


    /**
     * Collects user preferences flow from repo/datastore
     */
//    private fun loadUserPrefs() {
//
//        viewModelScope.launch(Dispatchers.IO) {
//
//            val up = useCases.getUserPreferencesUsecase().first()
//            Log.i(tag, "UserPrefs Loaded: $up")
//            _uiState.value = _uiState.value.copy(
//                userPrefs = up
//            )
//
////
////            useCases.getUserPreferencesUsecase().collectLatest {
////                _uiState.value = _uiState.value.copy(
////                    userPrefs = it
////                )
////                Log.i(tag, "UserPrefs Loaded: $it")
////            }
//        }
//    }


//    /**
//     * Updates the local copy of userPrefs, then updates the data store
//     */
//    private fun updateUserPrefs(userPrefs: UserPreferences) {
//        viewModelScope.launch(Dispatchers.IO) {
//
//            val newUiState = _uiState.value.copy(
//                userPrefs = userPrefs
//            )
//            _uiState.value = newUiState
//
//            // Update datastore
//            useCases.saveUserPreferencesUsecase(userPrefs)
//
//            Log.i(tag, "UserPrefs Updated: $userPrefs")
//
//        }
//    }
//
//
//    /**
//     * Updates the last selected watchlist in user prefs so that it can be loaded on the next screen load
//     */
//    private fun setUserPrefDefaultWatchlist(wlName: String) {
//        // Set wlName as default in prefs
//        if (_uiState.value.userPrefs != null) {
//            val prefs = _uiState.value.userPrefs
//            prefs!!.lastPopularWatchlistSelected = wlName
//            updateUserPrefs(prefs)
//            Log.i(tag, "UserPrefs lastPopularWatchlistSelected changed to: $wlName")
//        }
//    }


    /**
     * Loads futures data from Repo. Will be called on a loop using Launched Effect to refresh.
     */
    fun loadMajorFuturesData() {

        viewModelScope.launch(Dispatchers.IO) {

            useCases.getMajorFuturesUsecase().collectLatest {

                _uiState.value = _uiState.value.copy(
                    majorFutures = it
                )

                Log.i(tag, "MajorFutures Loaded: $it")
            }
        }
    }


    /**
     * Loads indices data from Repo. Will be called on a loop using Launched Effect to refresh.
     */
    fun loadMajorIndicesData() {

        viewModelScope.launch(Dispatchers.IO) {
            useCases.getMajorIndicesUsecase().collectLatest {
                _uiState.value = _uiState.value.copy(
                    majorIndices = it
                )

                Log.i(tag, "MajorIndices Loaded: $it")
            }
        }
    }


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

                Log.i(tag, "PopularWatchlistOptions Loaded: $it")
            }
        }
    }


    /**
     * Loads Popular watchlist to be displayed and sets UserPref.defaultWatchlist as wlName
     */
    fun loadWatchlist(wlName: String) {
        viewModelScope.launch(Dispatchers.IO) {

            // Load popular watchlist
            useCases.getPopularWatchlistUsecase(wlName).collectLatest {
                _uiState.value = _uiState.value.copy(
                    popularWatchlistDisplayed = it
                )
                Log.i(tag, "PopularWatchlist Loaded: ${it.toString()}")

                when (it) {
                    is DataState.Success -> {
                        Log.i(tag, "PopularWatchlist Success! Data: ${it.data}")
                    }
                    is DataState.Loading -> {
                        Log.i(tag, "PopularWatchlist Loading!")
                    }
                    is DataState.Error -> {
                        Log.i(tag, "PopularWatchlist Error! Msg: ${it.msg} " +
                                "Exception: ${it.exception}")
                    }
                }
            }
        }
    }


    /**
     * Handles Events that occur with the UI
     */
    fun handleEvent(event: DashboardUiEvent) {
        when (event) {
            is DashboardUiEvent.InitialLoad -> {
                // Load user prefs
                loadUserPrefs()

                // Load major futures
                loadMajorFuturesData()

                // Load major indices
                loadMajorIndicesData()

                // Load Wl Options
                loadPopularWatchlistOptions()

                // Load Default Wl
                loadDefaultWatchlist()
            }

            is DashboardUiEvent.RefreshData -> {
                loadMajorFuturesData()
                loadMajorIndicesData()

                // This shouldn't happen. Using safe way just in case
                val curWatchlist = _uiState.value.userPrefs?.lastPopularWatchlistSelected
                if (curWatchlist != null){
                    loadWatchlist(curWatchlist)
                }
            }

            is DashboardUiEvent.WatchlistOptionClicked -> {
                val wlName = event.name
                loadWatchlist(wlName)
                setUserPrefDefaultWatchlist(wlName)
            }

            is DashboardUiEvent.AddTickerToSentinelWatchlist -> {
                addTickerToSentinelWatchlist(event.ticker)
            }

            // Only here for the 'non-exhaustive when statement'
            else -> {

            }
        }
        Log.i(tag, "Dashboard Ui Event triggered. Event: $event")
    }

}
