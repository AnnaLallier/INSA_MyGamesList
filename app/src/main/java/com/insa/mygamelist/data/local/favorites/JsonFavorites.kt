package com.insa.mygamelist.data.local.favorites

import android.content.Context
import android.util.Log
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.io.File

/**
 * Handles the saving and loading of favorites from and to a JSON file
 */
object JsonFavorites {
    lateinit var favorites :List<Long>
    private const val FILE_NAME = "favorites.json"
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
        load()
    }

    private fun load() {
        val file = File(appContext.filesDir, FILE_NAME)

        Log.d("Loading of JSON Favorites data", "Start")

        if (file.exists()) {
            try {
                val json = file.readText()
                val favoritesFromJson : List<Long> = Gson().fromJson(
                    json,
                    object : TypeToken<List<Long>>() {}.type
                )
                favorites = favoritesFromJson.toMutableList()
            } catch (e: Exception) {
                Log.e("JsonFavorites", "Error loading favorites", e)
            }
        } else {
            Log.d("File favorites.json doesn't exist", "Initialization of an empty list")
            favorites = mutableListOf()
            saveFavorites()
        }

        Log.d("Favorites", favorites.toString())
        Log.d("Loading of JSON Favorites data", "Done")
    }

    fun addFavorite(id: Long) {
        if (!favorites.contains(id)) {
            favorites +=id
            saveFavorites()
        }
    }

    fun removeFavorite(id: Long) {
        if (favorites.contains(id)) {
            favorites -=id
            saveFavorites()
        }
    }

    private fun saveFavorites() {
        try {
            val json = Gson().toJson(favorites)
            val file = File(appContext.filesDir, FILE_NAME)
            Log.d("JsonFavorites", "File path : ${file.absolutePath}")
            appContext.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
                it.write(json.toByteArray())
            }
            //Log.d("JsonFavorites", "Favorites saved : $favorites")
            Log.d("JsonFavorites", "Saved content : ${file.readText()}")
        } catch (e: Exception) {
            Log.e("JsonFavorites", "Error when saving favorites", e)
        }
    }
}
