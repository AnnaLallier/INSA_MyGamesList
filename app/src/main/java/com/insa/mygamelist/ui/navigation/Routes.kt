package com.insa.mygamelist.ui.navigation

import com.insa.mygamelist.data.PlatformUpdated
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class PlatformDetail(
    val name : String,
    val platform_logo : Long
)

@Serializable
data class GameDetail(
    val id : Long,
    val cover : String,
    val genres :  List<String>, //TODO : try with set<Strign>
    val name : String,
    val platforms : List<PlatformUpdated>,
    val summary : String,
    val total_rating : Float,
)