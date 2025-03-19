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
    var offline = false

    //Research
    private val _filteredGames = MutableStateFlow<List<GameUpdated>>(emptyList())
    val filteredGames: StateFlow<List<GameUpdated>> = _filteredGames

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // Favorites
    private val _favorites = MutableStateFlow<List<Long>>(JsonFavorites.favorites)

    // Pagination
    private var currentPage = 1
    private val _isLoadingGames = MutableStateFlow(false)

    init {
        fetchGames()
    }

    /**
     * Toggle the favorite status of a game by adding or removing it from the JSON containing the favorites
     */
    fun toggleFavorite(gameId: Long) {
        if (_favorites.value.contains(gameId)) {
            JsonFavorites.removeFavorite(gameId)
        } else {
            JsonFavorites.addFavorite(gameId)
        }
        _favorites.value = JsonFavorites.favorites // Update the state
    }

    /**
     * Fetch the games from the API
     */
    fun fetchGames() {
        if (!_isLoadingGames.value) {
            _isLoadingGames.value = true
            viewModelScope.launch {
                try {
                    val gamesList = repository.getGames(currentPage)

                    // If the app is Offline
                    if (gamesList.isNullOrEmpty()) {
                        Log.d(
                            "API-UPDATE",
                            "Unable to get the data from Internet, it will be retrieved locally"
                        )
                        _games.value = IGDBAirplaneMode.games
                        offline = true
                    }

                    // If the app is Online
                    else {
                        _games.value += gamesList
                        currentPage++
                        IGDBAirplaneMode.games = _games.value
                        IGDBAirplaneMode.saveGames()
                        offline = false
                        _isLoadingGames.value = false

                    }

                    filterGames() // Filter the games according to the search query

                } catch (e: Exception) {
                    Log.e("API-ERROR", "Unable to retrieve the data from Internet or locally")
                }
            }
        }
        return // Do nothing if the games are already loading
    }

    /**
     * Update the search query and filter the games accordingly
     */
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        filterGames()
    }

    /**
     * Filter the games according to the search query
     */
    private fun filterGames() {
        _filteredGames.value = _games.value.filter { game ->
            val researchLowerCase = _searchQuery.value.lowercase()

            _searchQuery.value.isBlank() ||
                    game.name.lowercase().contains(researchLowerCase) ||
                    game.genres.any { it.lowercase().contains(researchLowerCase) } ||
                    game.platforms_names.any { it.lowercase().contains(researchLowerCase) }
        }
    }
}
