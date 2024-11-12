package com.thuc0502.tiktokdownloadwithoutwatermark.Activity

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.thuc0502.tiktokdownloadwithoutwatermark.R
import com.thuc0502.tiktokdownloadwithoutwatermark.databinding.ActivityUsingBinding

class UsingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUsingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUsingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.toolbar3)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = "Cách sử dụng ?"
        }

        val text = "Bước 1: Chọn video bạn muốn tải xuống và nhập vào biểu tượng CHIA SẺ"
        val text2 = "Bước 2: Tìm chọn SAO CHÉP LIÊN KẾT"
        val text3 = "Bước 3: Mở ứng dụng TikTok Downloader và chọn nút dán liên kết"
        val text4 = "Bước 4: Chọn tùy chọn và tải xuống"

        binding.textView5.text = boldText(text, "Bước 1", "CHIA SẺ")
        binding.textView6.text = boldText(text2, "Bước 2", "SAO CHÉP LIÊN KẾT")
        binding.textView7.text = boldText(text3, "Bước 3", "TikTok Downloader")
        binding.textView8.text = boldText(text4, "Bước 4", "tùy chọn và tải xuống")
    }

    fun boldText(
        text: String,
        keyword1: String,
        keyword2: String,
    ): SpannableStringBuilder {
        val spannable = SpannableStringBuilder(text)
        val boldSpan = StyleSpan(Typeface.BOLD)
        val boldSpa2 = StyleSpan(Typeface.BOLD)

        val start1 = text.toLowerCase().indexOf(keyword1.toLowerCase())
        val end1 = start1 + keyword1.length
        if (start1 != -1) {
            spannable.setSpan(boldSpa2, start1, end1, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        }

        val start2 = text.toLowerCase().indexOf(keyword2.toLowerCase())
        val end2 = start2 + keyword2.length
        if (start2 != -1) {
            spannable.setSpan(boldSpan, start2, end2, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        }

        return spannable
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                // Respond to the action bar's Up/Home button
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
}
