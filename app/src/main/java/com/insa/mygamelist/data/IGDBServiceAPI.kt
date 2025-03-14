package com.insa.mygamelist.data

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


class IGDBServiceAPI {
    private val gson = Gson()

    suspend fun getGames(): List<GameAPI>? {
        return withContext(Dispatchers.IO) {
            val query = "fields id, name, genres, cover, platforms; limit 10;"
            val requestBody: RequestBody = query.toRequestBody("text/plain".toMediaTypeOrNull())
            Log.d("API Request", "Request Body: $query")
            val response = IGDBClient.api.getGames(requestBody)

            if (response.isSuccessful && response.body() != null) {
                val json = response.body()!!.string()  // Convertir ResponseBody en String JSON
                Log.d("API Response", "Response Body: $json")
                gson.fromJson(
                    json,
                    object : TypeToken<List<GameAPI>>() {}.type
                )  // Convertir JSON en List<GameAPI>
            } else {
                Log.e("API Error", "Response failed: ${response.code()} - ${response.message()}")
                null
            }
        }
    }
}