package com.example.musicapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.ImageView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class PlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val videoId = intent.getStringExtra("VIDEO_ID") ?: return
        val songTitle = intent.getStringExtra("SONG_TITLE")
        val thumbnailUrl = intent.getStringExtra("THUMBNAIL_URL")

        findViewById<TextView>(R.id.songTitle).text = songTitle

        // Load thumbnail
        Glide.with(this)
            .load(thumbnailUrl)
            .into(findViewById(R.id.thumbnail))

        // Open in YouTube when play button tapped
        findViewById<Button>(R.id.playButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/watch?v=$videoId"))
            startActivity(intent)
        }
    }
}