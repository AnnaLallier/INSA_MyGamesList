package com.insa.mygamelist.data


import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

/**
 * Handles the saving and loading of the games in a JSON file
 */
object IGDBAirplaneMode {
    lateinit var games : List<GameUpdated>
    private const val FILE_NAME = "games_updated.json"
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
        load()
    }

    private fun load() {
        val file = File(appContext.filesDir, FILE_NAME)

        Log.d("Loading of JSON Games data", "Start")

        if (file.exists()) {
            try {
                val json = file.readText()
                val gamesFromJson: List<GameUpdated> = Gson().fromJson(
                    json,
                    object : TypeToken<List<GameUpdated>>() {}.type
                )
                games = gamesFromJson
            } catch (e: Exception) {
                Log.e("JsonFavorites", "Error loading favorites", e)
            }
        } else {
            Log.d("File games_updated.json doesn't exist", "Initialisation of an empty list")
            games = mutableListOf()
            saveGames()
        }
        Log.d("Loading of games JSON data", "Done")
    }

    fun saveGames() {
        try {
            val json = Gson().toJson(games)
            val file = File(appContext.filesDir, FILE_NAME)
            Log.d("JsonGamesUpdated", "Chemin du fichier : ${file.absolutePath}")
            appContext.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
                it.write(json.toByteArray())
            }
            //Log.d("JsonGamesUpdated", "Jeux enregistrés : $games")
            Log.d("JsonGamesUpdated", "Contenu enregistré : ${file.readText()}")
        } catch (e: Exception) {
            Log.e("JsonGamesUpdated", "Error saving games", e)
        }
    }
}