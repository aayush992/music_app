package com.example.musicapp.api

import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApiService {
    @GET("search")
    suspend fun searchSongs(
        @Query("part") part: String = "snippet",
        @Query("q") query: String,
        @Query("type") type: String = "video",
        @Query("videoCategoryId") categoryId: String = "10",
        @Query("maxResults") maxResults: Int = 20,
        @Query("videoEmbeddable") embeddable: String = "true",
        @Query("key") apiKey: String = "AIzaSyDd5wZEXo_SImrjXBa85alYZOsjfIhjZYo"
    ): YouTubeResponse
}

data class YouTubeResponse(val items: List<YouTubeItem>)
data class YouTubeItem(val id: VideoId, val snippet: Snippet)
data class VideoId(val videoId: String)
data class Snippet(val title: String, val channelTitle: String, val thumbnails: Thumbnails)
data class Thumbnails(val high: ThumbnailUrl)
data class ThumbnailUrl(val url: String)