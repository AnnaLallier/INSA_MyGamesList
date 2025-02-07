package com.insa.mygamelist.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
//import androidx.compose.foundation.Image
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.insa.mygamelist.data.Game
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.insa.mygamelist.data.IGDB



@Composable
fun GameCard(game: Game, model : IGDB, modifier : Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),

        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = "https:${IGDB.covers[game.cover]?.url}",
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Column {
                Text(game.name, fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline)
                val genres = game.genres.map { model.genres[it] }
                Text("Genres : ${genres.joinToString { it?.name ?: "" }}")
            }

        }
    }
}


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
            Button(onClick = {
                Log.d("CLICK", game.id.toString()) ;
                navController.navigate(GameDetail(game.id))
            })
            {
                GameCard(
                    game = game,
                    model = model,
                    modifier = Modifier.padding(8.dp) // Add proper spacing between cards
                )
            }
        }

    }
}

@Composable
fun GameScreen(id: GameDetail, model: IGDB, modifier: Modifier, onNavigateToGameList: () -> Unit) {
    Column(modifier = modifier) {
        val idJeu = id.id
        val game = model.games[idJeu] ?: return
        GameCard(game = game, model = model, modifier = Modifier.padding(8.dp))
        Spacer(modifier = Modifier.size(8.dp))
        Text(game.summary)
        Spacer(modifier = Modifier.size(8.dp))
        Button(onClick = onNavigateToGameList) {
            Text("Back to game list")
        }
    }
}

/*
@Preview
@Composable
fun GameCardPreview() {
    IGDB.load(this),

    GameCard(
        game = Game(
            id = 1,
            name = "Game Name",
            cover = 1,
            genres = setOf(123, 455),
            platforms = setOf(1, 2) ,
            first_release_date = 4562,
            summary = "This is a summary",
            total_rating = 4.5f

        ),
        model = IGDB,
        modifier = Modifier
    )
}*/