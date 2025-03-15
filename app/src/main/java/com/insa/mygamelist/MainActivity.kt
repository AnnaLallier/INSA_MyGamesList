package com.insa.mygamelist

import android.os.Bundle
import android.util.Log
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
import com.insa.mygamelist.data.Favorites
import com.insa.mygamelist.data.GameUpdated
import com.insa.mygamelist.data.GameViewModel
import com.insa.mygamelist.data.IGDB
import com.insa.mygamelist.data.JsonFavorites
import com.insa.mygamelist.ui.MyAppBar
import com.insa.mygamelist.ui.views.GameScreen
import com.insa.mygamelist.ui.navigation.Home
import com.insa.mygamelist.ui.navigation.Vue
import com.insa.mygamelist.ui.views.ListOfGames
import com.insa.mygamelist.ui.theme.MyGamesListTheme

class MainActivity : ComponentActivity() {

    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        IGDB.load(this)
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
            var vue = Vue.HOME
            var favorites = Favorites()

            when {
                dest!= null && dest.hasRoute<Home>() -> {
                    titre = "My Games List"
                    vue = Vue.HOME

                }
                dest != null && dest.hasRoute<GameUpdated>() -> {
                    val gameUpdated = currentBackStackEntryState?.toRoute<GameUpdated>()
                    titre = gameUpdated?.name ?: "Error when retrieving the title"
                    gameId = gameUpdated?.id ?: 0
                    isFavorite = Favorites.isFavorite(gameId)
                    vue = Vue.GAMEDETAIL
                }
            }

            MyGamesListTheme {
                Scaffold(topBar = {
                    MyAppBar(navController, titre, vue, gameId, isFavorite, gamesLoaded)
                }, modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = Home) {
                        composable<Home> {
                            ListOfGames(
                                games = gamesLoaded,
                                modifier = Modifier.padding(innerPadding),
                                navController
                            )
                        }
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
                    Log.d("INNER PADDING", innerPadding.toString())
                }
            }
        }
    }

    private fun quitApp() {
        finishAffinity()
    }
}

