package com.insa.mygamelist.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insa.mygamelist.data.local.IGDBAirplaneMode
import com.insa.mygamelist.data.local.favorites.JsonFavorites
import com.insa.mygamelist.data.model.GameUpdated
import com.insa.mygamelist.data.remote.IGDBServiceAPI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel used to
 * - Fetch the games from the API, either from the Internet or locally
 * - Toggle the favorite status of a game
 * - Handle the pagination
 */
class GameViewModel : ViewModel() {
    // General
    private val _games = MutableStateFlow<List<GameUpdated>>(emptyList())
    val games: StateFlow<List<GameUpdated>> = _games.asStateFlow()

    // API
    private val repository = IGDBServiceAPI()

    // Favorites
    private val _favorites = MutableStateFlow<List<Long>>(JsonFavorites.favorites)

    // Pagination
    private var currentPage = 1
    public var isLoadingGames = false

    init {
        fetchGames()
    }

    fun toggleFavorite(gameId: Long) {
        if (_favorites.value.contains(gameId)) {
            JsonFavorites.removeFavorite(gameId)
        } else {
            JsonFavorites.addFavorite(gameId)
        }
        _favorites.value = JsonFavorites.favorites // Update the state
    }

    fun fetchGames() {
        if (!isLoadingGames) {
            isLoadingGames = true
            viewModelScope.launch {
                try {
                    val gamesList = repository.getGames(currentPage)
                    if (gamesList.isNullOrEmpty()) {
                        Log.d(
                            "API-UPDATE",
                            "Unable to get the data from Internet, it will be retrieved locally"
                        )
                        _games.value = IGDBAirplaneMode.games

                    } else {
                        _games.value += gamesList
                        currentPage++
                        isLoadingGames = false
                        IGDBAirplaneMode.games = gamesList
                        IGDBAirplaneMode.saveGames()

                    }
                } catch (e: Exception) {
                    Log.e("API-ERROR", "Unable to retrieve the data from Internet or locally")
                }
            }
        }
        return // Do nothing if the games are already loading
    }
}
