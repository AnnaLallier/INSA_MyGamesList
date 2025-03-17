package com.insa.mygamelist.data.remote

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface used to define the API calls
 */
interface IGDBApi {

    @POST("games")
    suspend fun getGames(@Body body: RequestBody): Response<ResponseBody>

}