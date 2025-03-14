package com.insa.mygamelist.data

// méthodes suspend dans un CoroutineScope. Le Scope est combiné avec un Dispatcher pour indiquer sur quel Thread exécuter l'opération asynchrone
// Retrofit pour les appels réseaux
// DAns l'objet interface qui va servir à représenter l'API, utiliser @POST, @Body
//RequestBody pour les appels à IGDB

import android.util.Log
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.Response
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import com.google.gson.reflect.TypeToken
import okhttp3.Interceptor
import retrofit2.http.Header

// Client avec Retrofit
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


object IGDBClient {
    private const val BASE_URL = "https://api.igdb.com/v4/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(Interceptor {
            it.proceed(
                it.request().newBuilder()
                    .addHeader("Client-ID", "sg16951bcbb49ntm5w2ma13r5vqtje")
                    .addHeader("Authorization", "Bearer fw82xpyov5o46p7rwjxfjc1c0afr6l")
                    .build()
            )
        })
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

    val api: IGDBApi by lazy {
        retrofit.create(IGDBApi::class.java)
    }
}

/*
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
*/
data class GameAPI(
    val id: Long,
    val name: String,
    val genres: List<Long>?,  // Liste des IDs des genres
    val cover: Long?,         // ID de la cover
    val platforms: List<Long>? // Liste des IDs des plateformes
)

data class GenreAPI(val id: Long, val name: String)
data class CoverAPI(val id: Long, val url: String)
data class PlatformAPI(val id: Long, val name: String)
data class PlatformLogoAPI(val id: Long, val url: String)

class IGDBServiceAPI {
    private val gson = Gson()

    suspend fun getGames(): List<GameAPI>? {
        return withContext(Dispatchers.IO) {
            val query = "fields id, name, genres, cover, platforms; limit 10;"
            /*val query ="-H \"Content-Type: text/plain; charset=utf-8\" \\\n" +
                    "-H \"Client-ID: sg16951bcbb49ntm5w2ma13r5vqtje\" \\\n" +
                    "-H \"Authorization: Bearer ygp92co7qdz0usoelrdlk6xbnz5ieb\" \\\n" +
                    "--data \"fields id, name, genres, cover, platforms; limit 10;\""
*/
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

class GameViewModel : ViewModel() {
    private val repository = IGDBServiceAPI()

    private val _games = MutableLiveData<List<GameAPI>>()
    val games: LiveData<List<GameAPI>> get() = _games

    fun fetchGames() {
        viewModelScope.launch {
            val gamesList = repository.getGames()
            _games.postValue(gamesList ?: emptyList())
        }
    }
}

//interface IGDBServiceAPI {
// Que des suspend
/*suspend fun getData() : List<Game> {
val result = IGDBApi.getData().await()
return result.map { it.toGame() }
}*/

//}