package com.insa.mygamelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.insa.mygamelist.data.Game
import com.insa.mygamelist.data.IGDB
import com.insa.mygamelist.ui.theme.GameDetail
import com.insa.mygamelist.ui.theme.GameScreen
import com.insa.mygamelist.ui.theme.Home
import com.insa.mygamelist.ui.theme.ListOfGames
import com.insa.mygamelist.ui.theme.MyGamesListTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        IGDB.load(this)


        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            MyGamesListTheme {

                Scaffold(/*topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            Text("My Games List")
                        })
                }, */modifier = Modifier.fillMaxSize()) { innerPadding ->
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
                            val id : GameDetail = backStackEntry.toRoute()
                            GameScreen( //mettre en paramètre la route récup depuis NavController
                                id = id,
                                model = IGDB,
                                modifier = Modifier.padding(innerPadding),
                                onNavigateToGameList = { navController.navigate(route = Home) }
                            )
                        }
                    }
                    //MyApp(games = IGDB.games, model = IGDB, modifier = Modifier.padding(innerPadding))
                    //ListOfGames(games = IGDB.games, model = IGDB, modifier = Modifier.padding(innerPadding))

                    //GameCard(game = IGDB.games[28278]!!, model = IGDB, modifier = Modifier.padding(innerPadding))

                    //Text("À remplir", modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}