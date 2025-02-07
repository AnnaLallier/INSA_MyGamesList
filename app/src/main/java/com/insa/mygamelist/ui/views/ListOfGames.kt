package com.insa.mygamelist.ui.views

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    navController: NavController
) {
    LazyColumn(modifier = modifier) {
        items(games.size) { index ->
            val game = games.values.elementAt(index)
            val genres = game.genres.map { model.genres[it]?.name ?: "" }
            Log.d("GENRES", genres.toString())

            Button(onClick = {
                Log.d("REDIRECT TO GAME", game.id.toString()) ;
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
            })
            {
                GameCard(
                    game = game,
                    genres = genres,
                    modifier = Modifier.padding(8.dp) // Add proper spacing between cards
                )
            }
        }

    }
}