package com.example.musicapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.adapter.SongAdapter
import com.example.musicapp.api.RetrofitClient
import com.example.musicapp.model.Song
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchInput: EditText
    private lateinit var searchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        searchInput = findViewById(R.id.searchInput)
        searchButton = findViewById(R.id.searchButton)

        recyclerView.layoutManager = LinearLayoutManager(this)

        searchButton.setOnClickListener {
            val query = searchInput.text.toString()
            if (query.isNotEmpty()) searchSongs(query)
        }
    }

    private fun searchSongs(query: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.searchSongs(query = query)
                val songs = response.items.map {
                    Song(
                        videoId = it.id.videoId,
                        title = it.snippet.title,
                        channelName = it.snippet.channelTitle,
                        thumbnailUrl = it.snippet.thumbnails.high.url
                    )
                }
                val adapter = SongAdapter(songs) { song ->
                    val intent = Intent(this@MainActivity, PlayerActivity::class.java)
                    intent.putExtra("VIDEO_ID", song.videoId)
                    intent.putExtra("SONG_TITLE", song.title)
                    intent.putExtra("THUMBNAIL_URL", song.thumbnailUrl)
                    startActivity(intent)
                }
                recyclerView.adapter = adapter
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}