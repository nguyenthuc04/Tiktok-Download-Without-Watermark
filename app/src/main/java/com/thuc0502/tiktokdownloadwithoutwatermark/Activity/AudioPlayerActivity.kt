package com.thuc0502.tiktokdownloadwithoutwatermark.Activity

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.thuc0502.tiktokdownloadwithoutwatermark.R
import com.thuc0502.tiktokdownloadwithoutwatermark.databinding.ActivityAudioPlayerBinding

class AudioPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAudioPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v ,insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left ,systemBars.top ,systemBars.right ,systemBars.bottom)
            insets
        }
        val audioPath = intent.getStringExtra("AUDIO_PATH")
        val mediaController = MediaController(this)
        mediaController.setAnchorView(binding.mediaPlayer)

        // Thiết lập Chế độ xem video
        binding.mediaPlayer.apply {
            setMediaController(mediaController)
            setVideoURI(Uri.parse(audioPath))
            requestFocus()
            start()
        }
        binding.backButton.setOnClickListener {
            finish()
        }
    }

}