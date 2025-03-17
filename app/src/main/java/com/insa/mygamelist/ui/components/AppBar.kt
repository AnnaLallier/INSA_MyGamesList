package com.insa.mygamelist.ui.components

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
import com.insa.mygamelist.data.local.favorites.Favorites
import com.insa.mygamelist.data.model.GameUpdated
import com.insa.mygamelist.ui.navigation.Home
import com.insa.mygamelist.ui.navigation.NameOfView

/**
 * AppBar of the application
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(navController : NavController, titre : String, nameOfView : NameOfView, gameId : Long, isFavorite : Boolean, games : List<GameUpdated>) {
    val actionRetour: (() -> Unit)?
    val showDialogSearch = remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }
    Log.d("MyAppBar", "gameId: $gameId, isFavorite: $isFavorite")

    when {
        nameOfView == NameOfView.HOME && !showDialogSearch.value -> {
            val activity = (LocalContext.current as? Activity)
            actionRetour = { activity?.finish() }

        }

        nameOfView == NameOfView.GAMEDETAIL -> {
            actionRetour = { navController.navigateUp() }
        }

        nameOfView == NameOfView.HOME && showDialogSearch.value -> {
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
                if (nameOfView != NameOfView.GAMEDETAIL) {
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
                else if (nameOfView == NameOfView.GAMEDETAIL) {
                    var rememberIsFavorite by remember { mutableStateOf(isFavorite) }

                    IconButton(

                        onClick = {
                            if (rememberIsFavorite) {
                                Favorites.removeFavorite(gameId)
                            } else {
                                Favorites.addFavorite(gameId)
                            }
                            rememberIsFavorite = !rememberIsFavorite
                        },
                        modifier = Modifier.size(40.dp)
                    ) {
                        if (rememberIsFavorite) {
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

        if (showDialogSearch.value && nameOfView == NameOfView.HOME) {
            MySearchBar(
                PaddingValues(start=0.0.dp, top=88.0.dp, end=0.0.dp, bottom=24.0.dp),
                navController,
                query, 
                isActive,
                { it -> query = it },
                { it -> isActive = it },
                games
            )
        }
    }
}