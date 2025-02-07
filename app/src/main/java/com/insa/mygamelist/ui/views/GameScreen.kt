package com.insa.mygamelist.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.insa.mygamelist.data.Game
import com.insa.mygamelist.ui.navigation.GameDetail

@Composable
fun GameScreen(gameDetail: GameDetail, modifier: Modifier, onNavigateToGameList: () -> Unit) {
    Column(modifier = modifier) {
        val game = Game(
            id = gameDetail.id,
            cover = gameDetail.cover,
            first_release_date = gameDetail.first_release_date,
            genres = setOf<Long>(),
            name = gameDetail.name,
            platforms = gameDetail.platforms.toSet(),
            summary = gameDetail.summary,
            total_rating = gameDetail.total_rating
        )
        GameCard(game = game, genres = gameDetail.genres, Modifier.padding(8.dp))
        Spacer(modifier = Modifier.size(8.dp))
        Text(game.summary)
        Spacer(modifier = Modifier.size(8.dp))
        Button(onClick = onNavigateToGameList) {
            Text("Back to game list")
        }
    }
}
