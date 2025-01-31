package com.insa.mygamelist.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.insa.mygamelist.R

object IGDB {


    lateinit var covers: MutableMap<Long, Cover>
    lateinit var genres: MutableMap<Long, Genre>
    lateinit var platformLogos: MutableMap<Long, PlatformLogo>
    lateinit var platforms: MutableMap<Long, Platform>
    lateinit var games: MutableMap<Long, Game>

    fun load(context: Context) {

        covers = mutableMapOf()
        genres = mutableMapOf()
        platformLogos = mutableMapOf()
        platforms = mutableMapOf()
        games = mutableMapOf()

        val coversFromJson: List<Cover> = Gson().fromJson(
            context.resources.openRawResource(R.raw.covers).bufferedReader(),
            object : TypeToken<List<Cover>>() {}.type
        )

        val genresFromJson: List<Genre> = Gson().fromJson(
            context.resources.openRawResource(R.raw.genres).bufferedReader(),
            object : TypeToken<List<Genre>>() {}.type
        )

        val platformLogosFromJson: List<PlatformLogo> = Gson().fromJson(
            context.resources.openRawResource(R.raw.platform_logos).bufferedReader(),
            object : TypeToken<List<PlatformLogo>>() {}.type
        )

        val platformsFromJson: List<Platform> = Gson().fromJson(
            context.resources.openRawResource(R.raw.platforms).bufferedReader(),
            object : TypeToken<List<Platform>>() {}.type
        )

        val gamesFromJson: List<Game> = Gson().fromJson(
            context.resources.openRawResource(R.raw.games).bufferedReader(),
            object : TypeToken<List<Game>>() {}.type
        )

        for (cover in coversFromJson) {
            covers[cover.id] = cover
        }

        for (genre in genresFromJson) {
            genres[genre.id] = genre
        }

        for (platformLogo in platformLogosFromJson) {
            platformLogos[platformLogo.id] = platformLogo
        }

        for (platform in platformsFromJson) {
            platforms[platform.id] = platform
        }

        for (game in gamesFromJson) {
            games[game.id] = game
        }

        Log.d("Loading of JSON data", "Start")
        Log.d("Covers", covers.toString())
        Log.d("Genres", genres.toString())
        Log.d("PlatformLogos", platformLogos.toString())
        Log.d("Platforms", platforms.toString())
        Log.d("Games", games.toString())
        Log.d("Loading of JSON data", "Done")


    }
}

data class Cover(val id: Long, val url: String)

data class Genre(val id : Long, val name : String)

data class PlatformLogo(val id : Long, val url : String)

data class Platform(val id : Long,
                    val name : String,
                    val platform_logo : Long)

data class Game(
    val id : Long,
    val cover : Long,
    val first_release_date : Long,
    val genres :  Set<Long>,
    val name : String,
    val platforms : Set<Long>,
    val summary : String,
    val total_rating : Float
)

