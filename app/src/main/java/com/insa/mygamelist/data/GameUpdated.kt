package com.insa.mygamelist.data

import kotlinx.serialization.Serializable

@Serializable
data class GameUpdated(
    val id : Long,
    val cover : String,
    val genres : List<String>,
    val name : String,
    val platforms_names : List<String>,
    val platforms_logos : List<Long>,
    val summary : String,
    val total_rating : Float
)

@Serializable
data class PlatformUpdated(
    val name : String,
    val platform_logo : Long
)
