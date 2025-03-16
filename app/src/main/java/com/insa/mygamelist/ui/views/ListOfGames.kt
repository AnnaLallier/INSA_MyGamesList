package com.insa.mygamelist.ui.views

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
import com.insa.mygamelist.data.GameUpdated
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


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
                //TODO : I removed the tolist to genres so check if it still works correctly
                game.name.lowercase().contains(researchLowerCase) ||
                game.genres.any { it.lowercase().contains(researchLowerCase) } ||
                game.platforms.any { it.name.lowercase().contains(researchLowerCase) }
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
                val gameJson = Json.encodeToString(game)
                GameCard(
                    game = game,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            Log.d("REDIRECT TO GAME", game.id.toString())
                            navController.navigate(
                                "game_detail/$gameJson"
                            )
                        }
                )
            }
        }
    }
}


