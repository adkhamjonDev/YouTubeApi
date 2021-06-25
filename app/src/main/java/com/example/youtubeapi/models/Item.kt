package com.example.youtubeapi.models

import java.io.Serializable

data class Item(
    val etag: String,
    val id: Id,
    val kind: String,
    val snippet: Snippet
) : Serializable