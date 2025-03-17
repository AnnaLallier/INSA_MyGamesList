package com.insa.mygamelist.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insa.mygamelist.data.local.IGDBAirplaneMode
import com.insa.mygamelist.data.model.GameUpdated
import com.insa.mygamelist.data.remote.IGDBServiceAPI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel used to fetch the games from the API, either from the Internet or locally
 */
class GameViewModel : ViewModel() {
    private val repository = IGDBServiceAPI()

    private val _games = MutableStateFlow<List<GameUpdated>>(emptyList())
    val games: StateFlow<List<GameUpdated>> = _games.asStateFlow()

    fun fetchGames() {
        viewModelScope.launch {
            try {
                val gamesList = repository.getGames()
                if (gamesList.isNullOrEmpty()) {
                    Log.d(
                        "API-UPDATE",
                        "Unable to get the data from Internet, it will be retrieved locally"
                    )
                    _games.value = IGDBAirplaneMode.games

                } else {
                    _games.value = gamesList
                    IGDBAirplaneMode.games = gamesList
                    IGDBAirplaneMode.saveGames()

                }
            } catch(e: Exception) {
                Log.e("API-ERROR", "Unable to retrieve the data from Internet or locally")
            }
        }
    }
}
