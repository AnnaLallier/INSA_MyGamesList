package com.insa.mygamelist.ui.components


import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil3.compose.AsyncImage
import com.insa.mygamelist.data.model.GameUpdated
import com.insa.mygamelist.ui.viewmodel.GameViewModel

/**
 * Component displaying a card for a game with its cover, name, genres, and favorite button
 */
@Composable
fun GameCard(game: GameUpdated, modifier: Modifier) {
    val gameViewModel: GameViewModel = ViewModelProvider(LocalContext.current as ComponentActivity).get(GameViewModel::class.java)
    val favorites by gameViewModel.favorites.collectAsState()
    val isFavorite = favorites.contains(game.id)

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = "https:${game.cover}",
                contentDescription = "Game Cover",
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    game.name,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
                Text("Genres : ${game.genres.joinToString()}")
            }


            IconButton(
                onClick = {
                    gameViewModel.toggleFavorite(game.id)
                },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (isFavorite) "Remove from Favorites" else "Add to Favorites",
                    modifier = Modifier.size(40.dp)
                )
            }
        }

    }
}
