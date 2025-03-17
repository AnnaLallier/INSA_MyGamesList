package com.insa.mygamelist.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.insa.mygamelist.data.model.GameUpdated


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(onDismissRequest: () -> Unit, innerPadding : PaddingValues, navController: NavController, query : String, varisActive : Boolean, onQueryChange: (String) -> Unit, onActiveChange: (Boolean) -> Unit, games : List<GameUpdated>) {

    SearchBar(
        query = query,
        onQueryChange = { onQueryChange(it) },
        onSearch = { onActiveChange(false) },
        active = varisActive,
        onActiveChange = { onActiveChange(it) },
        placeholder = { Text("Search for a game, a genre, or a platform") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Close, contentDescription = "Clear Search")
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {

        ListOfGames(
            games = games,
            modifier = Modifier.padding(innerPadding),
            navController,
            research = query
        )
    }
}
