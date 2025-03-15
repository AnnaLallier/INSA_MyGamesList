package com.insa.mygamelist

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.google.common.base.Predicates.instanceOf
import com.insa.mygamelist.data.Favorites
import com.insa.mygamelist.data.GameViewModel
import com.insa.mygamelist.data.IGDB
import com.insa.mygamelist.data.JsonFavorites
import com.insa.mygamelist.ui.MyAppBar
import com.insa.mygamelist.ui.navigation.GameDetail
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
        gameViewModel.games.observe(this) { games ->
            Log.d("MainActivity", "Jeux reçus : $games")
        }
        gameViewModel.fetchGames() // Déclenche la requête API

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            // recup l'état courant dans la back
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
                dest != null && dest.hasRoute<GameDetail>() -> {
                    val gameDetail = currentBackStackEntryState?.toRoute<GameDetail>()
                    titre = gameDetail?.name ?: "Erreur lors de la récupération du titre"
                    gameId = gameDetail?.id ?: 0
                    isFavorite = Favorites.isFavorite(gameId)
                    vue = Vue.GAMEDETAIL
                }
                else -> {
                    "Erreur lors de la récupération du titre"
                }
            }

            MyGamesListTheme {
                Scaffold(topBar = {
                    MyAppBar(navController, titre, vue, gameId, isFavorite)
                }, modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = Home) {
                        composable<Home> {
                            ListOfGames(
                                games = IGDB.games,
                                model = IGDB,
                                modifier = Modifier.padding(innerPadding),
                                navController
                            )
                        }
                        composable<GameDetail> {
                            backStackEntry ->
                            val gameDetail : GameDetail = backStackEntry.toRoute()
                            GameScreen(
                                gameDetail = gameDetail,
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

