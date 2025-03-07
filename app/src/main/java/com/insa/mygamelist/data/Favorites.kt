package com.insa.mygamelist.data

import android.content.Context
import android.util.Log
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.insa.mygamelist.R

object JsonFavorites {
    lateinit var favorites :List<Long>

    fun load(context : Context) {
        /*val favoritesFromJson: List<Long> = Gson().fromJson(
            context.resources.openRawResource(R.raw.favorites).bufferedReader(),
            object : TypeToken<List<Long>>() {}.type
        )*/

        //favorites = favoritesFromJson
        favorites = emptyList()

        Log.d("Loading of JSON data", "Start")
        Log.d("Favorites", favorites.toString())
        Log.d("Loading of JSON data", "Done")
    }
}


class Favorites {

    var favorite : Long? = null

    companion object {
        fun addFavorite(id: Long) {
            Log.d("AJOUT FAVORIS", id.toString())
            JsonFavorites.favorites += id
            Log.d("FAVORITES", JsonFavorites.favorites.toString())

        }
        fun removeFavorite(id: Long) {
            JsonFavorites.favorites -= id
        }
        fun isFavorite(id: Long): Boolean {
            if (JsonFavorites.favorites.contains(id)) {
                Log.d("FAVORI $id", "true")
            } else {
                Log.d("FAVORI $id", "false")
            }
            return JsonFavorites.favorites.contains(id)
        }
    }

}