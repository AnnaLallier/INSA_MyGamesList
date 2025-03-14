package com.insa.mygamelist.data

// méthodes suspend dans un CoroutineScope. Le Scope est combiné avec un Dispatcher pour indiquer sur quel Thread exécuter l'opération asynchrone
// Retrofit pour les appels réseaux
// DAns l'objet interface qui va servir à représenter l'API, utiliser @POST, @Body
//RequestBody pour les appels à IGDB

import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.http.GET

// Client avec Retrofit
object IGDBApi {
    var retrofit = Retrofit.Builder()
    .baseUrl("https://api.igdb.com/v4/games")
    .build()


}

interface IGDBServiceAPI {
    // Que des suspend
    @GET("curl -X POST \"https://api.igdb.com/v4/games\" -H \"Client-ID: sg16951bcbb49ntm5w2ma13r5vqtje\" -H \"Authorization: Bearer ygp92co7qdz0usoelrdlk6xbnz5ieb\" -d \"fields *; limit 10;\"\n")
     suspend fun getData() : List<Game> {
        val result = IGDBApi.getData().await()
        return result.map { it.toGame() }
    }

}