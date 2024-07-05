package com.thuc0502.tiktokdownloadwithoutwatermark.Activity

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.thuc0502.tiktokdownloadwithoutwatermark.R
import com.thuc0502.tiktokdownloadwithoutwatermark.databinding.ActivityVideoPlayerBinding

class VideoPlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v ,insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left ,systemBars.top ,systemBars.right ,systemBars.bottom)
            insets
        }
        // Nhận đường dẫn video từ Intent
        val videoPath = intent.getStringExtra("VIDEO_PATH")

        // Tạo một MediaController
        val mediaController = MediaController(this)
        mediaController.setAnchorView(binding.videoView)

        // Thiết lập Chế độ xem video
        binding.videoView.apply {
            setMediaController(mediaController)
            setVideoURI(Uri.parse(videoPath))
            requestFocus()
            start()
        }
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}