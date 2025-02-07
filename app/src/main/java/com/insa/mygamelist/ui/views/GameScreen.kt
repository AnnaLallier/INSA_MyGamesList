package com.insa.mygamelist.ui.views

import android.util.Log
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.insa.mygamelist.data.Game
import com.insa.mygamelist.data.IGDB
import com.insa.mygamelist.data.IGDB.genres
import com.insa.mygamelist.ui.navigation.GameDetail

@Composable
fun GameScreen(gameDetail: GameDetail, modifier: Modifier, onNavigateToGameList: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(8.dp)
                .fillMaxSize()

    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        brush = Brush.linearGradient(
                            colors  = listOf(Color.Black, Color.Red)
                        )
                    )
                ) {
                    append(gameDetail.name)
                }
            },
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline,
        )
        AsyncImage(
            model = "https:${IGDB.covers[gameDetail.cover]?.url}",
            contentDescription = null,
            modifier = Modifier.size(250.dp)
        )
        var genres = gameDetail.genres.toString()
        genres = genres.replace("[", "")
        genres = genres.replace("]", "")
        Text(
            genres,
            fontStyle = FontStyle.Italic,
            color = Color.Gray
        )
        Log.d("GENRES", gameDetail.genres[0])
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
                .horizontalScroll(rememberScrollState())

        ) {
            gameDetail.platforms.forEach {
                AsyncImage(
                    model = "https:${IGDB.platformLogos[IGDB.platforms[it]?.platform_logo]?.url}",
                    contentDescription = null,
                    //contentScale = ContentScale.Crop, rend tout carré mais déforme un peu trop les images
                    modifier = Modifier
                        .size(75.dp)
                        .height(75.dp)
                        .padding(8.dp)
                )
            }
        }
        Text (
            "Summary",
            textDecoration = TextDecoration.Underline,
        )
        Text(
            gameDetail.summary,
            textAlign = TextAlign.Justify,
            modifier =
                Modifier.verticalScroll(rememberScrollState())
                    .weight(1f)

        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateToGameList) {
            Text("Back to game list")
        }
    }
}
