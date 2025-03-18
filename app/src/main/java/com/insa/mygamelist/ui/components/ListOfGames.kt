package com.insa.mygamelist.ui.components

import android.util.Log
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    games: List<GameUpdated>,
    modifier: Modifier,
    navController: NavController,
    research : String = "" // The research to filter the games
) {

    val gameViewModel: GameViewModel = ViewModelProvider(LocalContext.current as ComponentActivity).get(
        GameViewModel::class.java)
    val filteredGames = games.filter { game ->
        val researchLowerCase = research.lowercase()

        research.isBlank() || // If the research is empty, all the games are displayed
                // If it's not, only the games that contain the research in their name, genres or platforms are displayed
                game.name.lowercase().contains(researchLowerCase) ||
                game.genres.any { it.lowercase().contains(researchLowerCase) } ||
                game.platforms_names.any { it.lowercase().contains(researchLowerCase) }
    }

    if (filteredGames.isEmpty()) {
        Text(
            text = "No games :(",
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center,
        )
    } else {
        LazyColumn(modifier = modifier) {
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
            item {
                if (gameViewModel.isLoadingGames) {
                    Log.d("PAGINATION", "Loading games")
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth().padding(16.dp))
                } else if (filteredGames.isNotEmpty()) {
                    LaunchedEffect(filteredGames.size) {
                        Log.d("PAGINATION", "Load more games")
                        gameViewModel.fetchGames()
                    }

                }
            }
        }
    }
}


