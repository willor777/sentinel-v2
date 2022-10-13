package com.willor.sentinel_v2.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willor.lib_data.domain.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    val repo: Repo          // TODO Replace with Use Cases
): ViewModel() {

    private val _testFlow = MutableStateFlow("Nothing Yet")
    val testFlow = _testFlow.asStateFlow()

    fun test(){
        viewModelScope.launch(Dispatchers.IO) {
            _testFlow.value = repo.getMajorFutures()
        }
    }



}