package com.insa.mygamelist.data

import kotlinx.serialization.Serializable

@Serializable
data class GameUpdated(
    val id : Long,
    val cover : String,
    val genres : List<String>,
    val name : String,
    val platforms : List<PlatformUpdated>,
    val summary : String,
    val total_rating : Float
)

