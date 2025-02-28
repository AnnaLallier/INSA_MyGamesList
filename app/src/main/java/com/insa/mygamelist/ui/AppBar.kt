package com.insa.mygamelist.ui

import android.app.Activity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.navigation.NavController
import com.insa.mygamelist.ui.navigation.Home
import com.insa.mygamelist.ui.navigation.Vue
import com.insa.mygamelist.ui.views.SearchAlert

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun myAppBar(navController : NavController, titre : String, vue : Vue) {
    var actionRetour : (() -> Unit)? = null
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
    when {
        showDialogSearch.value -> {
            SearchAlert(  onDismissRequest = { showDialogSearch.value = false })
        }
    }
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
            IconButton(onClick = {
                showDialogSearch.value = true
            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search"
                )
            }
        },
    )
}