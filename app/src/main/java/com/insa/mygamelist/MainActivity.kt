package com.insa.mygamelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.insa.mygamelist.data.local.favorites.Favorites
import com.insa.mygamelist.data.model.GameUpdated
import com.insa.mygamelist.ui.viewmodel.GameViewModel
import com.insa.mygamelist.data.local.IGDBAirplaneMode
import com.insa.mygamelist.data.local.favorites.JsonFavorites
import com.insa.mygamelist.ui.components.MyAppBar
import com.insa.mygamelist.ui.components.GameScreen
import com.insa.mygamelist.ui.navigation.Home
import com.insa.mygamelist.ui.navigation.NameOfView
import com.insa.mygamelist.ui.components.ListOfGames
import com.insa.mygamelist.ui.theme.MyGamesListTheme

/**
 * Main activity of the application
 */
class MainActivity : ComponentActivity() {

    private lateinit var gameViewModel: GameViewModel // View model to get the games

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        IGDBAirplaneMode.init(this)
        JsonFavorites.init(this)

        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        gameViewModel.fetchGames() // Start the API call to get the games

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val gamesLoaded by gameViewModel.games.collectAsState() // Get the games

            val currentBackStackEntryState by navController.currentBackStackEntryAsState()
            val dest = currentBackStackEntryState?.destination
            var titre = String()
            var gameId : Long = 0
            var isFavorite = false
            var nameOfView = NameOfView.HOME

            when {
                // Check the current destination to display the correct title
                dest!= null && dest.hasRoute<Home>() -> {
                    titre = "My Games List"
                    nameOfView = NameOfView.HOME

                }
                dest != null && dest.hasRoute<GameUpdated>() -> {
                    val gameUpdated = currentBackStackEntryState?.toRoute<GameUpdated>()
                    titre = gameUpdated?.name ?: "Error when retrieving the title"
                    gameId = gameUpdated?.id ?: 0
                    isFavorite = Favorites.isFavorite(gameId) // Display the correct icon if the game is a favorite or not
                    nameOfView = NameOfView.GAMEDETAIL
                }
            }

            MyGamesListTheme {
                Scaffold(topBar = {
                    MyAppBar(navController, titre, nameOfView, gameId, isFavorite, gamesLoaded)
                }, modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = Home) {
                        // Home screen, displaying the list of games
                        composable<Home> {
                            ListOfGames(
                                modifier = Modifier.padding(innerPadding),
                                navController
                            )
                        }
                        // Game screen, displaying the details of a game
                        composable<GameUpdated> {
                                backStackEntry ->
                            val gameUpdated : GameUpdated = backStackEntry.toRoute()
                            GameScreen(
                                gameUpdated = gameUpdated,
                                modifier = Modifier.padding(innerPadding),
                                onNavigateToGameList = { navController.navigate(route = Home) }
                            )
                        }
                    }
                }
            }
        }
    }
}

