package com.insa.mygamelist.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.insa.mygamelist.data.model.GameUpdated


/**
 * Component displaying a list of games
 */
@Composable
fun ListOfGames(
    games: List<GameUpdated>,
    modifier: Modifier,
    navController: NavController,
    research : String = ""
) {
    val filteredGames = games.filter { game ->
        val researchLowerCase = research.lowercase()

        research.isBlank() ||
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
        }
    }
}


