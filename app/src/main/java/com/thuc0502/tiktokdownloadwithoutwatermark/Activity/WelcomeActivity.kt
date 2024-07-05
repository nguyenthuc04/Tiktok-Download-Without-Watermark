package com.thuc0502.tiktokdownloadwithoutwatermark.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.thuc0502.tiktokdownloadwithoutwatermark.R
import com.thuc0502.tiktokdownloadwithoutwatermark.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v ,insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left ,systemBars.top ,systemBars.right ,systemBars.bottom)
            insets
        }
        binding.seekBar.max = 100
        val delayMillis = 20L // 2 giây (100 * 20ms = 2000ms = 2s)

        runnable = Runnable {
            val progress = binding.seekBar.progress + 1
            if (progress > binding.seekBar.max) {
                // Nếu seekBar đạt giá trị tối đa, khởi động MainActivity
                startActivity(Intent(this ,MainActivity::class.java))
                finish()
            } else {
                //  không thì cập nhật tiến trình và đăng lại độ trễ
                binding.seekBar.progress = progress
                handler.postDelayed(runnable ,delayMillis)
            }
        }

        // Start updating seekBar
        handler.postDelayed(runnable ,delayMillis)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Xóa lệnh gọi lại khi hoạt động bị hủy để tránh rò rỉ bộ nhớ
        handler.removeCallbacks(runnable)
    }
}