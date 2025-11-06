package com.example.racerapplication.ui.detail


import androidx.lifecycle.ViewModel
import com.example.racerapplication.data.model.Race
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel : ViewModel() {
    private val _race = MutableStateFlow<Race?>(null)
    val race: StateFlow<Race?> = _race.asStateFlow()

    fun setRace(race: Race) {
        _race.value = race
    }
}