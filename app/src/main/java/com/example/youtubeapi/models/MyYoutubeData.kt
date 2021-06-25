package com.example.youtubeapi.models

import java.io.Serializable

data class MyYoutubeData(
    val etag: String,
    val items: ArrayList<Item>,
    val kind: String,
    val nextPageToken: String,
    val pageInfo: PageInfo,
    val regionCode: String
): Serializable