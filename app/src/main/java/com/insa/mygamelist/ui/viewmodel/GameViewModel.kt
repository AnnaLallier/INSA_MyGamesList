package com.insa.mygamelist.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insa.mygamelist.data.local.IGDBAirplaneMode
import com.insa.mygamelist.data.local.JsonFavorites
import com.insa.mygamelist.data.model.GameUpdated
import com.insa.mygamelist.data.remote.IGDBServiceAPI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel used to
 * - Fetch the games from the API, either from the Internet or locally
 * - Toggle the favorite status of a game
 * - Toggle the display of only the favorite games
 * - Handle the pagination
 */
class GameViewModel : ViewModel() {
    // General
    private val _games = MutableStateFlow<List<GameUpdated>>(emptyList())

    // API
    private val repository = IGDBServiceAPI()
    private var _offline = MutableStateFlow(false)
    var offline : StateFlow<Boolean> = _offline

    // Favorites
    private val _favorites = MutableStateFlow<List<Long>>(JsonFavorites.favorites)
    var favorites : StateFlow<List<Long>> = _favorites

    // Pagination
    private var currentPage = 1
    private val _isLoadingGames = MutableStateFlow(false)

    //Research
    private val _filteredGames = MutableStateFlow<List<GameUpdated>>(emptyList())
    val filteredGames: StateFlow<List<GameUpdated>> = _filteredGames

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // ---- Research and favorites
    private val _toggleFavoritesOnly = MutableStateFlow(false)
    val toggleFavoritesOnly: StateFlow<Boolean> = _toggleFavoritesOnly
    private val _allFilteredGames = MutableStateFlow<List<GameUpdated>>(emptyList())



    init {
        fetchGames()
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
                        _offline.value = true
                        _isLoadingGames.value = false
                    }

                    // If the app is Online
                    else {
                        Log.d("API-UPDATE", "Data retrieved from the Internet")

                        _games.value += gamesList.filter { newGame -> _games.value.none { it.id == newGame.id } }
                        currentPage++
                        IGDBAirplaneMode.games = _games.value
                        IGDBAirplaneMode.saveGames()
                        _offline.value = false
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
        if (_toggleFavoritesOnly.value) {
            _allFilteredGames.value = _filteredGames.value
            _filteredGames.value = _filteredGames.value.filter { game ->
                _favorites.value.contains(game.id)
            }
        }
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
     * Toggle the display of only the favorite games
     */
    fun toggleShowOnlyFavorites() {
        if (_toggleFavoritesOnly.value) {
            _toggleFavoritesOnly.value = false
            _filteredGames.value = _allFilteredGames.value
        } else {
            _toggleFavoritesOnly.value = true
            _allFilteredGames.value = _filteredGames.value
            _filteredGames.value = _filteredGames.value.filter { game ->
                _favorites.value.contains(game.id)
            }
        }
    }

}
