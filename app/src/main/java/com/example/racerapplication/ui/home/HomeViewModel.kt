package com.example.racerapplication.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.racerapplication.data.model.Driver
import com.example.racerapplication.data.model.Race
import com.example.racerapplication.data.repository.RaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class HomeUiState(
    val isLoading: Boolean = true,
    val driver: Driver? = null,
    val race: Race? = null,
    val error: String? = null
)

class HomeViewModel : ViewModel() {
    private val repository = RaceRepository()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = HomeUiState(isLoading = true)

            val driverResult = repository.getTopDriver()
            val raceResult = repository.getUpcomingRace()

            _uiState.value = HomeUiState(
                isLoading = false,
                driver = driverResult.getOrNull(),
                race = raceResult.getOrNull(),
                error = when {
                    driverResult.isFailure && raceResult.isFailure ->
                        "Failed to load data"

                    driverResult.isFailure -> "Failed to load driver"
                    raceResult.isFailure -> "Failed to load race"
                    else -> null
                }
            )
        }
    }
}
