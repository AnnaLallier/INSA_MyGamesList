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
import com.insa.mygamelist.data.Game
import com.insa.mygamelist.data.IGDB
import com.insa.mygamelist.ui.navigation.GameDetail


@Composable
fun ListOfGames(
    games: Map<Long, Game>,
    model: IGDB,
    modifier: Modifier,
    navController: NavController,
    research : String = ""
) {
    val filteredGames = games.values.filter { game ->
        val genres = game.genres.map { model.genres[it]?.name ?: "" }
        val researchLowerCase = research.lowercase()

        research.isBlank() ||
                game.name.lowercase().contains(researchLowerCase) ||
                genres.any { it.lowercase().contains(researchLowerCase) } ||
                game.platforms.toList().any { IGDB.platforms[it]?.name?.lowercase()?.contains(researchLowerCase) == true }
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
                val genres = game.genres.map { model.genres[it]?.name ?: "" }
                GameCard(
                    game = game,
                    genres = genres,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            Log.d("REDIRECT TO GAME", game.id.toString())
                            navController.navigate(
                                GameDetail(
                                    game.id,
                                    game.cover,
                                    game.first_release_date,
                                    genres,
                                    game.name,
                                    game.platforms.toList(),
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
