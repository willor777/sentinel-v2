package com.willor.sentinel_v2.presentation.home




import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willor.lib_data.data.local.prefs.UserPreferences
import com.willor.lib_data.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject


//    // TODO When these flows are NetworkState.Loading... Show an animation.
//    // TODO When they are NetworkState.Error... Show a "Something went wrong image"
//    // TODO Maybe make a UserPref field for the last selected watchlist, to be loaded on reload.

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState get() = _uiState

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
        }
    }

    /**
     * Collects user preferences flow from repo/datastore
     */
    private fun loadUserPrefs(){

        viewModelScope.launch(Dispatchers.IO) {
            useCases.getUserPreferencesUsecase().collectLatest {
                _uiState.value = _uiState.value.copy(
                    userPrefs = it
                )
            }
        }
    }


    /**
     * Updates the local copy of userPrefs, then updates the data store
     */
    private fun updateUserPrefs(userPrefs: UserPreferences){
        viewModelScope.launch(Dispatchers.IO){

            // Update local copy
            _uiState.value = _uiState.value.copy(
                userPrefs = userPrefs
            )

            // Update datastore
            useCases.saveUserPreferencesUsecase(userPrefs)
        }
    }


    /**
     * Loads futures data from Repo. Should be called on a loop using Launched Effect from homescreen.
     */
    fun loadMajorFuturesData(){

        viewModelScope.launch(Dispatchers.IO){

            useCases.getMajorFuturesUsecase().collectLatest {

                _uiState.value = _uiState.value.copy(
                    majorFutures = it
                )
            }
        }
    }


    /**
     * Loads the PopularWatchlistOptions from Repo. When Success -> loadWatchlist() is called
     * with the first watchlist in the options list.
     */
    fun loadPopularWatchlistOptions(){
        viewModelScope.launch(Dispatchers.IO){
            useCases.getPopularWatchlistOptionsUsecase().collectLatest {

                _uiState.value = _uiState.value.copy(
                    popularWatchlistOptions = it
                )

            }
        }
    }


    /**
     * Loads Popular watchlist to be displayed and sets UserPref.defaultWatchlist as wlName
     */
    fun loadWatchlist(wlName: String){
        viewModelScope.launch(Dispatchers.IO){

            // Set wlName as default in prefs
            if (_uiState.value.userPrefs != null){
                val prefs = _uiState.value.userPrefs
                prefs!!.lastPopularWatchlistSelected = wlName
                updateUserPrefs(prefs)
            }

            // Load popular watchlist
            useCases.getPopularWatchlistUsecase(wlName).collectLatest {
                _uiState.value = _uiState.value.copy(
                    popularWatchlistDisplayed = it
                )
            }

        }
    }


    /**
     * Handles Events that occur with the UI
     */
    fun handleEvent(event: HomeUiEvent){
        when (event){
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
        }
    }

}








//}
//
//
//
