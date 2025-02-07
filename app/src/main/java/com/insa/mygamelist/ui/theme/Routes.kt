package com.insa.mygamelist.ui.theme

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.insa.mygamelist.data.Game
import com.insa.mygamelist.data.Genre
import com.insa.mygamelist.data.IGDB
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class GameDetail(
    val id : Long,
    val cover : Long,
    val first_release_date : Long,
    val genres :  List<String>,
    val name : String,
    val platforms : List<Long>,
    val summary : String,
    val total_rating : Float,
)


/*
@Serializable
data class Profile(val name: String)

@Serializable
object FriendsList

// Define the ProfileScreen composable.
@Composable
fun ProfileScreen(
    profile: Profile,
    onNavigateToFriendsList: () -> Unit,
) {
    Text("Profile for ${profile.name}")
    Button(onClick = { onNavigateToFriendsList() }) {
        Text("Go to Friends List")
    }
}

@Composable
fun ListOfGames(games : Map<Long, Game>, model : IGDB, modifier : Modifier, onNavigateToProfile: () -> Unit) {
    LazyColumn(modifier = modifier) {
        items(games.size) { index ->
            GameCard(
                game = games.values.elementAt(index),
                model = model,
                modifier = Modifier.padding(8.dp) // Add proper spacing between cards
            )
        }
    }
}

// Define the MyApp composable, including the `NavController` and `NavHost`.
@Composable
fun MyApp(games : Map<Long, Game>, model : IGDB, modifier : Modifier) {
    val navController = rememberNavController()
    NavHost(navController, startDestination =
    Profile(name = "John Smith")) {
        composable<Profile> { backStackEntry ->
            val profile: Profile = backStackEntry.toRoute()
            ListOfGames(
                games = games, model = IGDB, modifier = modifier,
                onNavigateToProfile = {
                    navController.navigate(route = FriendsList)
                }
            )
        }
        composable<FriendsList> {
            FriendsListScreen(
                onNavigateToProfile = {
                    navController.navigate(
                        route = Profile(name = "Aisha Devi")
                    )
                }
            )
        }
    }
}
*/