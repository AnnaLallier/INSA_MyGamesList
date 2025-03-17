package com.insa.mygamelist.data

import kotlinx.serialization.Serializable

/**
 * Data class used to represent a game and its information
 */
@Serializable
data class GameUpdated(
    val id : Long,
    val cover : String,
    val genres : List<String>,
    val name : String,
    val platforms_names : List<String>,
    val platforms_url : List<String>,
    val summary : String,
    val total_rating : Float
)
