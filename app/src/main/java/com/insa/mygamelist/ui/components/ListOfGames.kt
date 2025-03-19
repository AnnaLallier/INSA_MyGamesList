package com.insa.mygamelist.ui.components

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.insa.mygamelist.data.model.GameUpdated
import com.insa.mygamelist.ui.viewmodel.GameViewModel


/**
 * Component displaying a list of games
 */
@Composable
fun ListOfGames(
    modifier: Modifier,
    navController: NavController
) {

    val gameViewModel: GameViewModel =
        ViewModelProvider(LocalContext.current as ComponentActivity).get(GameViewModel::class.java)
    val filteredGames by gameViewModel.filteredGames.collectAsState()
    val searchQuery by gameViewModel.searchQuery.collectAsState()

    Column(modifier = modifier.fillMaxSize()) { // Column to display the list of games
        when { // When no games are found
            filteredGames.isEmpty() && searchQuery != "" -> {
                //Log.d("NO GAMES", "No games found")

                if (!gameViewModel.offline) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth().padding(16.dp))
                }

                Text(
                    text = "No games :(",
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center,
                )
            }

            else -> { // When games are found
                LazyColumn( // Display the list of games
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(filteredGames) { game ->
                        GameCard(
                            game = game,
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    Log.d("REDIRECT TO GAME", game.id.toString())
                                    navController.navigate(
                                        GameUpdated(
                                            game.id,
                                            game.cover,
                                            game.genres,
                                            game.name,
                                            game.platforms_names,
                                            game.platforms_url,
                                            game.summary,
                                            game.total_rating,
                                        )
                                    )
                                }
                        )
                    }

                    item { // Progress bar and offline message
                        if (gameViewModel.offline) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Info,
                                    contentDescription = "Offline",
                                )
                                Text(
                                    text = "You are offline",
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                            }
                        } else {
                            LaunchedEffect(filteredGames.size / 2) {
                                Log.d("PAGINATION", "Instruction : load more games")
                                gameViewModel.fetchGames()
                            }

                            LinearProgressIndicator(modifier = Modifier.fillMaxWidth().padding(16.dp))

                        }
                    }

                }
            }
        }
    }
}
