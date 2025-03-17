package com.insa.mygamelist.ui.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.insa.mygamelist.data.local.favorites.Favorites
import com.insa.mygamelist.data.model.GameUpdated

/**
 * Component displaying a card for a game with its cover, name, genres, and favorite button
 */
@Composable
fun GameCard(game: GameUpdated, modifier: Modifier) {
    var isFavorite by remember { mutableStateOf(Favorites.isFavorite(game.id)) }

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
                contentDescription = null,
                modifier = Modifier.size(100.dp)
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
                    if (isFavorite) {
                        Favorites.removeFavorite(game.id)
                    } else {
                        Favorites.addFavorite(game.id)
                    }
                    isFavorite = !isFavorite
                },
                modifier = Modifier.size(40.dp)
            ) {
                if (isFavorite) {
                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = "Remove from Favorites",
                        modifier = Modifier.size(40.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Add to Favorites",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }

    }
}
