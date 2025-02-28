package com.insa.mygamelist.ui.views


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.insa.mygamelist.data.IGDB

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(onDismissRequest: () -> Unit, innerPadding : PaddingValues, navController: NavController) {
    var query by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }

    SearchBar(
        query = query,
        onQueryChange = { query = it },
        onSearch = { isActive = false }, // Trigger search action
        active = isActive,
        onActiveChange = { isActive = it },
        placeholder = { Text("Search for a game, a genre, or a platform") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { query = "" }) {
                    Icon(Icons.Default.Close, contentDescription = "Clear Search")
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        ListOfGames(
            games = IGDB.games,
            model = IGDB,
            modifier = Modifier.padding(innerPadding),
            navController,
            research = query
        )
    }
}
