package com.example.youtubeapi.models

import java.io.Serializable
data class Id(
    val kind: String,
    val videoId: String
): Serializable