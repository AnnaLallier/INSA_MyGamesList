package com.insa.mygamelist.ui

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.navigation.NavController
import com.insa.mygamelist.ui.navigation.Home
import com.insa.mygamelist.ui.navigation.Vue
import com.insa.mygamelist.ui.views.MySearchBar
import com.insa.mygamelist.ui.views.SearchAlert

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(navController : NavController, titre : String, vue : Vue) {
    var actionRetour: (() -> Unit)? = null
    var showDialogSearch = remember { mutableStateOf(false) }

    when {
        vue == Vue.HOME -> {
            val activity = (LocalContext.current as? Activity)
            actionRetour = { activity?.finish() }

        }

        vue == Vue.GAMEDETAIL -> {
            actionRetour = { navController.navigate(route = Home) }
        }

        else -> {
            actionRetour = { navController.navigate(route = Home) }
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
            }
        )

        if (showDialogSearch.value && vue == Vue.HOME) {
            MySearchBar(onDismissRequest = { showDialogSearch.value = false }, PaddingValues(start=0.0.dp, top=88.0.dp, end=0.0.dp, bottom=24.0.dp), navController)
        }
    }
}