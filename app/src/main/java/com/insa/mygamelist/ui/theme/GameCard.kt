package com.insa.mygamelist.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.insa.mygamelist.data.IGDB

@Composable
fun GameCard(game: Game, model : IGDB, modifier : Modifier) {
    Card(
        modifier = modifier
            .background(Color.Black)
            .padding(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = "https:${IGDB.covers[game.cover]?.url}",
                contentDescription = null,
                modifier = modifier.padding(5.dp)
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
fun ListOfGames(games : Map<Long, Game>, model : IGDB, modifier : Modifier) {
    LazyColumn {
        items(games.size) { index ->
            GameCard(game = games.values.elementAt(index), model = model, modifier = modifier)
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}

