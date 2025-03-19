package com.insa.mygamelist.ui.components

import android.util.Log
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.insa.mygamelist.data.model.GameUpdated

/**
 * Component displaying the details of a game
 */
@Composable
fun GameScreen(gameUpdated: GameUpdated, modifier: Modifier, onNavigateToGameList: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(8.dp)
                .fillMaxSize()

    ) {
        /* Title, useless now because of the AppBar
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
        )  */

        // Cover image
        Box( // The box is needed to resize the image and use clip
            modifier = Modifier.fillMaxWidth(.5f)
        ) {
            AsyncImage(
                model = "https:${gameUpdated.cover}",
                contentDescription = "Game Cover",
                modifier = Modifier
                    .padding(3.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()

            )
        }

        // Genres
        var genres = gameUpdated.genres.toString()
        genres = genres.replace("[", "").replace("]", "")
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
            gameUpdated.platforms_url.forEach {
                Log.d("PLATFORM URL", "https:${it}")
                AsyncImage(
                    model = "https:${it}",
                    contentDescription = "Platform logos",
                    //contentScale = ContentScale.Crop, makes everything square but alters the image
                    modifier = Modifier
                        .size(105.dp)
                        .height(75.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))

                )
            }
        }
        Text (
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        brush = Brush.linearGradient(
                            colors  = listOf(Color(0xFF0f1552), Color(0xFF92A6E1))
                        )
                    )
                ) {
                    append("Summary")
                }
            },
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
        Button(
            onClick = onNavigateToGameList,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF495A9A),

            )
        ) {
            Text("BACK TO GAME LIST")
        }
    }
}
