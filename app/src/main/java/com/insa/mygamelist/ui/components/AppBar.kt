package com.insa.mygamelist.ui.components

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.insa.mygamelist.ui.navigation.Home
import com.insa.mygamelist.ui.navigation.NameOfView
import com.insa.mygamelist.ui.viewmodel.GameViewModel

/**
 * AppBar of the application
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(navController : NavController, titre : String, nameOfView : NameOfView, gameId : Long, isFavorite : Boolean) {
    val actionRetour: (() -> Unit)?
    val showDialogSearch = remember { mutableStateOf(false) }
    val gameViewModel: GameViewModel = ViewModelProvider(LocalContext.current as ComponentActivity).get(GameViewModel::class.java)

    // Sets the action to do when the back button (<-) is pressed
    when {
        // From the home screen, close the app
        nameOfView == NameOfView.HOME && !showDialogSearch.value -> {
            val activity = (LocalContext.current as? Activity)
            actionRetour = { activity?.finish() } // Close the app
        }

        // From the game details screen, go back to the home screen or the search screen
        nameOfView == NameOfView.GAMEDETAIL -> {
            actionRetour = { navController.navigateUp() } // Go back to the previous screen
        }

        // From the search screen, go back to the home screen
        nameOfView == NameOfView.HOME && showDialogSearch.value -> {
            actionRetour = {
                gameViewModel.updateSearchQuery("") // Clear the search query, very much useful!
                navController.navigate(route = Home) // Go back to the home screen
                showDialogSearch.value = false // Hide the search bar
            }
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
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
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
                            gameViewModel.toggleFavorite(gameId)
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
            MySearchBar()
        }
    }
}