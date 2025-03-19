package com.insa.mygamelist.ui.components


import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.insa.mygamelist.ui.viewmodel.GameViewModel

/** Search bar of the application */
@Composable
fun MySearchBar() {
    val gameViewModel: GameViewModel = ViewModelProvider(LocalContext.current as ComponentActivity).get(GameViewModel::class.java)
    val query by gameViewModel.searchQuery.collectAsState()

    MyCustomSearchBar(
        query = query,
        onQueryChange = { gameViewModel.updateSearchQuery(it) }
    )
}

@Composable
fun MyCustomSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search")
            },
            modifier = Modifier.weight(1f),
            placeholder = { Text("Search for a game, a genre, or a platform") },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear Search", tint = Color.Gray)
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedLabelColor = Color.Transparent,
                )
        )
    }
}
