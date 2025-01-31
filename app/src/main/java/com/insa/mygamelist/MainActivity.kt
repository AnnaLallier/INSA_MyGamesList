package com.insa.mygamelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.insa.mygamelist.data.IGDB
import com.insa.mygamelist.ui.theme.GameCard
import com.insa.mygamelist.ui.theme.ListOfGames
import com.insa.mygamelist.ui.theme.MyGamesListTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        IGDB.load(this)


        enableEdgeToEdge()
        setContent {

            MyGamesListTheme {
                Scaffold(topBar = {
                    TopAppBar(colors = topAppBarColors(
                        containerColor = Color.LightGray,
                        titleContentColor = Color.Black,
                    ), title = { Text("My Games List") })
                }, modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ListOfGames(games = IGDB.games, model = IGDB, modifier = Modifier.padding(innerPadding))

                    //GameCard(game = IGDB.games[28278]!!, model = IGDB, modifier = Modifier.padding(innerPadding))

                    //Text("À remplir", modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}