package com.insa.mygamelist.data

import kotlinx.serialization.Serializable

@Serializable
data class PlatformUpdated(val name : String, val platform_logo : Long)
