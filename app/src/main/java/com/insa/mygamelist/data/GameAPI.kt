package com.insa.mygamelist.data

data class GameAPI(
    val id: Long,
    val name: String,
    val genres: List<Long>?,  // Liste des IDs des genres
    val cover: Long?,         // ID de la cover
    val platforms: List<Long>? // Liste des IDs des plateformes
)

data class GenreAPI(val id: Long, val name: String)
data class CoverAPI(val id: Long, val url: String)
data class PlatformAPI(val id: Long, val name: String)
data class PlatformLogoAPI(val id: Long, val url: String)

