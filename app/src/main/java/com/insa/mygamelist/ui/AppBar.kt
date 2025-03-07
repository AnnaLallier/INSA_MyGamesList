package com.insa.mygamelist.ui

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.insa.mygamelist.data.Favorites
import com.insa.mygamelist.ui.navigation.Home
import com.insa.mygamelist.ui.navigation.Vue
import com.insa.mygamelist.ui.views.MySearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(navController : NavController, titre : String, vue : Vue, gameId : Long) {
    var actionRetour: (() -> Unit)? = null
    var showDialogSearch = remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(Favorites.isFavorite(gameId)) }
    Log.d("MyAppBar", "gameId: $gameId, isFavorite: $isFavorite")

    when {
        vue == Vue.HOME && !showDialogSearch.value -> {
            val activity = (LocalContext.current as? Activity)
            actionRetour = { activity?.finish() }

        }

        vue == Vue.GAMEDETAIL -> {
            actionRetour = { navController.navigateUp() }
        }

        vue == Vue.HOME && showDialogSearch.value -> {
            actionRetour = { navController.navigate(route = Home)
                showDialogSearch.value = false}

        }

        else -> {
            actionRetour = { navController.navigateUp() }
        }
    }

    Column {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(
                    titre,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = { actionRetour() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go Back"
                    )
                }
            },

            actions = {
                if (vue != Vue.GAMEDETAIL) {
                    IconButton(onClick = {
                        if (showDialogSearch.value) {
                            showDialogSearch.value = false
                        } else {
                            showDialogSearch.value = true
                        }
                    }) {
                        if (showDialogSearch.value) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close"
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search"
                            )
                        }
                    }
                }
                else if (vue == Vue.GAMEDETAIL) {
                    IconButton(
                        onClick = {
                            if (isFavorite) {
                                Favorites.removeFavorite(gameId)
                            } else {
                                Favorites.addFavorite(gameId)
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
        )

        if (showDialogSearch.value && vue == Vue.HOME) {
            MySearchBar(onDismissRequest = { showDialogSearch.value = false }, PaddingValues(start=0.0.dp, top=88.0.dp, end=0.0.dp, bottom=24.0.dp), navController, query, isActive, { it -> query = it }, { it -> isActive = it })
        }
        else if (!showDialogSearch.value && vue == Vue.GAMEDETAIL) {

        }
    }
}