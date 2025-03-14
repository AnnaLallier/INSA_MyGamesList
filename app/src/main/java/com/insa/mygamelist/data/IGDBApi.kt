package com.insa.mygamelist.data

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IGDBApi {

    @POST("games")
    suspend fun getGames(@Body body: RequestBody): Response<ResponseBody>

    @POST("genres")
    suspend fun getGenres(@Body body: RequestBody): Response<ResponseBody>

    @POST("covers")
    suspend fun getCovers(@Body body: RequestBody): Response<ResponseBody>

    @POST("platforms")
    suspend fun getPlatforms(@Body body: RequestBody): Response<ResponseBody>

    @POST("platform_logos")
    suspend fun getPlatformLogos(@Body body: RequestBody): Response<ResponseBody>
}