package com.insa.mygamelist.data.favorites

/** Class used to interact with JsonFavorites */
class Favorites {
    companion object {
        fun addFavorite(id: Long) {
            JsonFavorites.addFavorite(id)
        }
        fun removeFavorite(id: Long) {
            JsonFavorites.removeFavorite(id)
        }
        fun isFavorite(id: Long): Boolean {
            return JsonFavorites.favorites.contains(id)
        }
    }
}