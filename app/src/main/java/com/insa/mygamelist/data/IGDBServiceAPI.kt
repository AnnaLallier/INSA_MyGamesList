package com.insa.mygamelist.data

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


class IGDBServiceAPI {
    private val gson = Gson()

    suspend fun getGames(): List<GameUpdated>? {
        return withContext(Dispatchers.IO) {
            // Request that gets everything needed for the game list, where the platform logo is not null
            val queryAll = "fields id, cover.id, cover.url, first_release_date, genres.id, genres.name, name, platforms.id, platforms.name, platforms.platform_logo, summary, total_rating; limit 10; where platforms.platform_logo !=null;"
            val requestBody: RequestBody = queryAll.toRequestBody("text/plain".toMediaTypeOrNull())

            // Calls the API with the request
            val response = IGDBClient.api.getGames(requestBody)

            if (response.isSuccessful && response.body() != null) {
                val gamesJson = response.body()!!.string()  // Gets the JSON from the response

                val gamesList = gson.fromJson<List<JsonObject>>(gamesJson, object : TypeToken<List<JsonObject>>() {}.type)

                val finalGamesList = gamesList.map {
                    gameJson -> GameUpdated(
                        id = gameJson["id"].asLong,
                        cover = gameJson["cover"]?.asJsonObject?.get("url")?.asString ?: "",
                        genres = gameJson["genres"]?.asJsonArray?.map {
                            it.asJsonObject["name"].asString
                        }?.toSet() ?: emptySet(),
                        name = gameJson["name"].asString,
                        platforms = gameJson["platforms"]?.asJsonArray?.map {
                            platformJson -> PlatformUpdated(
                                name = platformJson.asJsonObject["name"].asString,
                                platform_logo = platformJson.asJsonObject["platform_logo"]?.asLong ?: 0L
                            )
                        } ?: emptyList(),
                        summary = gameJson["summary"]?.asString ?: "",
                        total_rating = gameJson["total_rating"]?.asFloat ?: 0.0f
                    )
                }

                return@withContext finalGamesList
            } else {
                Log.e("API Error", "Response failed: ${response.code()} - ${response.message()}")
                null
            }
        }
    }
}