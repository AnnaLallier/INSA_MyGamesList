package com.insa.mygamelist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    private val repository = IGDBServiceAPI()

    private val _games = MutableStateFlow<List<GameUpdated>>(emptyList())
    val games: StateFlow<List<GameUpdated>> = _games.asStateFlow()

    fun fetchGames() {
        viewModelScope.launch {
            val gamesList = repository.getGames()
            _games.value = gamesList ?: emptyList()
        }
    }
}
