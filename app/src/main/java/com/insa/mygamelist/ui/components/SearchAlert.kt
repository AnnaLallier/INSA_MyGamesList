package com.insa.mygamelist.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

// I made this class then realized later that it was not needed, so I replaced it with SearchBar, but I keep it here since it worked..

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAlert(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(
                text = "Search for something...",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                textAlign = TextAlign.Center,
            )
            /*SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                inputField = TODO(),
                expanded = TODO(),
                onExpandedChange = TODO(),
                shape = TODO(),
                colors = TODO(),
                tonalElevation = TODO(),
                shadowElevation = TODO(),
                windowInsets = TODO(),
            ) { }*/
        }
    }
}