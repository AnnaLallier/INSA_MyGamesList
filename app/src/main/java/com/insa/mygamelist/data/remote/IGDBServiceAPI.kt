package com.insa.mygamelist.data.remote

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.insa.mygamelist.data.model.GameUpdated
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import retrofit2.HttpException
import java.net.ConnectException


/**
 * Service used to fetch the games from the API
 */
class IGDBServiceAPI {
    private val gson = Gson()

    private fun query(currentPage : Int) : String {
        // Request that gets everything needed for the game list, where the platform logo url is not null
        val queryAll = "" +
                "fields id, cover.id, cover.url, first_release_date, genres.id, genres.name, name, platforms.name, platforms.platform_logo.url, summary, total_rating; " +
                "limit 100; " +
                "offset ${currentPage * 10}; " +
                "where platforms.platform_logo.url != null" +
                "& genres != null;" +
                //"& total_rating > 75;" +
                "sort total_rating desc; "
        return queryAll
    }

    suspend fun getGames(currentPage : Int): List<GameUpdated>? {
        return withContext(Dispatchers.IO) {

            val queryAll = query(currentPage)

            val requestBody: RequestBody = queryAll.toRequestBody("text/plain".toMediaTypeOrNull())

            try {
                // Calls the API with the request
                val response = IGDBClient.api.getGames(requestBody)

                if (response.isSuccessful && response.body() != null) {
                    val gamesJson = response.body()!!.string()  // Gets the JSON from the response

                    val gamesList = gson.fromJson<List<JsonObject>>(
                        gamesJson,
                        object : TypeToken<List<JsonObject>>() {}.type
                    )

                    val finalGamesList = gamesList.map { gameJson ->
                        GameUpdated(
                            id = gameJson["id"].asLong,
                            cover = gameJson["cover"]?.asJsonObject?.get("url")?.asString?.replace("t_thumb", "t_cover_big") ?: "",
                            genres = gameJson["genres"]?.asJsonArray?.map {
                                it.asJsonObject["name"].asString
                            }?.toList() ?: emptyList(),
                            name = gameJson["name"].asString,
                            platforms_names = gameJson["platforms"]?.asJsonArray?.map {
                                it.asJsonObject["name"].asString
                            }?.toList() ?: emptyList(),
                            platforms_url = gameJson["platforms"]?.asJsonArray?.mapNotNull {
                                it.asJsonObject["platform_logo"]?.asJsonObject?.get("url")?.asString?.replace("t_thumb", "t_logo_med")?.replace("jpg", "png")
                            }?.toList() ?: emptyList(),
                            summary = gameJson["summary"]?.asString ?: "",
                            total_rating = gameJson["total_rating"]?.asFloat ?: 0.0f
                        )
                    }

                    return@withContext finalGamesList
                } else {
                    Log.e(
                        "API Error",
                        "Response failed: ${response.code()} - ${response.message()}"
                    )
                    null
                }
            } catch (e:ConnectException) {
                Log.e("Connection Error", "ConnectException : ${e.message}")
                null
            } catch (e : IOException) {
                Log.e("API Error", "IOException: ${e.message}")
                null
            } catch (e: HttpException) {
                Log.e("HTTP Error", "HTTP Exception: ${e.code()} - ${e.message}")
                null
            } catch (e: Exception) {
                Log.e("Unknown Error", "Unexpected error: ${e.localizedMessage}")
                null
            }

        }
    }
}