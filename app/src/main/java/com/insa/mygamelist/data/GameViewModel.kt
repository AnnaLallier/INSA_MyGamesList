package com.insa.mygamelist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    private val repository = IGDBServiceAPI()

    private val _games = MutableLiveData<List<GameUpdated>>()
    val games: LiveData<List<GameUpdated>> get() = _games

    fun fetchGames() {
        viewModelScope.launch {
            val gamesList = repository.getGames()
            _games.postValue(gamesList ?: emptyList())
        }
    }
}
