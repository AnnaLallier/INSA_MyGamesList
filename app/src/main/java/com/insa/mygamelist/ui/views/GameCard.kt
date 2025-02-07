package com.insa.mygamelist.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.insa.mygamelist.data.Game
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.insa.mygamelist.data.IGDB

@Composable
fun GameCard(game: Game, genres : List<String>, modifier : Modifier) {
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
                Text("Genres : ${genres.joinToString()}")
            }

        }
    }
}


