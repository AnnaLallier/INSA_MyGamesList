package com.insa.mygamelist.ui.views

import android.util.Log
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.insa.mygamelist.data.GameUpdated

@Composable
fun GameScreen(gameUpdated: GameUpdated, modifier: Modifier, onNavigateToGameList: () -> Unit) {
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
                    append(gameUpdated.name)
                }
            },
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline,
        )
        AsyncImage(
            model = "https:${gameUpdated.cover}",
            contentDescription = null,
            modifier = Modifier.size(250.dp)
        )
        var genres = gameUpdated.genres.toString() //TODO : see if it's useful
        genres = genres.replace("[", "")
        genres = genres.replace("]", "")
        Text(
            genres,
            fontStyle = FontStyle.Italic,
            color = Color.Gray
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
                .horizontalScroll(rememberScrollState())

        ) {
            gameUpdated.platforms_logos.forEach {
                Log.d("PLATFORM_LOGO", "https:${it}");
                AsyncImage(
                    model = "https:${it}",
                    contentDescription = null,
                    //contentScale = ContentScale.Crop, makes everything square but alters the image
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
            gameUpdated.summary,
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
