package com.insa.mygamelist.data

data class GameUpdated(
    val id : Long,
    val cover : String,
    val genres : Set<String>,
    val name : String,
    val platforms : List<PlatformUpdated>,
    val summary : String,
    val total_rating : Float
)