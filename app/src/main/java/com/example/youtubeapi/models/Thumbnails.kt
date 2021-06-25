package com.example.youtubeapi.models

import java.io.Serializable

data class Thumbnails(
    val default: Default,
    val high: High,
    val medium: Medium
): Serializable