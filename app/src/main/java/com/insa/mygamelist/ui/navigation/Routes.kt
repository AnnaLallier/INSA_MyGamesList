package com.insa.mygamelist.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class GameDetail(
    val id : Long,
    val cover : Long,
    val first_release_date : Long,
    val genres :  List<String>,
    val name : String,
    val platforms : List<Long>,
    val summary : String,
    val total_rating : Float,
)