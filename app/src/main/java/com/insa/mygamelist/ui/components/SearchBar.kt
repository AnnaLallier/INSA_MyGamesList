package com.insa.mygamelist.ui.components


import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.insa.mygamelist.data.model.GameUpdated
import com.insa.mygamelist.ui.viewmodel.GameViewModel


/** Search bar of the application */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(
    innerPadding: PaddingValues,
    navController: NavController
) {
    val gameViewModel: GameViewModel = ViewModelProvider(LocalContext.current as ComponentActivity).get(GameViewModel::class.java)
    val query by gameViewModel.searchQuery.collectAsState()

    SearchBar(
        query = query,
        onQueryChange = { gameViewModel.updateSearchQuery(it) },
        onSearch = {  },
        active = query.isNotEmpty(),
        onActiveChange = { },
        placeholder = { Text("Search for a game, a genre, or a platform") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { gameViewModel.updateSearchQuery("") }) {
                    Icon(Icons.Default.Close, contentDescription = "Clear Search")
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        ListOfGames(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
