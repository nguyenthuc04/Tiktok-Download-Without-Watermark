package com.thuc0502.tiktokdownloadwithoutwatermark.Activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.thuc0502.tiktokdownloadwithoutwatermark.Fragment.VideoInfoBottomSheetFragment
import com.thuc0502.tiktokdownloadwithoutwatermark.Model.TikTokVideoInfo
import com.thuc0502.tiktokdownloadwithoutwatermark.R
import com.thuc0502.tiktokdownloadwithoutwatermark.databinding.ActivityMainBinding
import com.thuc0502.tiktokdownloadwithoutwatermark.extensions.showToast
import com.thuc0502.tiktokdownloadwithoutwatermark.getTikTokVideoInfo
import com.thuc0502.tiktokdownloadwithoutwatermark.isValidUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var lastVideoInfo: TikTokVideoInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
        setupListeners()
    }

    private fun setupUI() {
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v ,insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left ,systemBars.top ,systemBars.right ,systemBars.bottom)
            insets
        }
    }

    private fun setupListeners() {
        setupEditTextTouchListener()
        setupLinkButtonClickListener()
        setupOpenTikTokButtonClickListener()
        setupStorageDownloadButtonClickListener()
        setupReviewButtonClickListener()
        setupUsageButtonClickListener()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupEditTextTouchListener() {
        binding.editTextText.setOnTouchListener { _ ,event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (binding.editTextText.right - binding.editTextText.compoundDrawables[2].bounds.width())) {
                    binding.editTextText.setText("")
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    private fun setupLinkButtonClickListener() {
        binding.btnDanLienKet.setOnClickListener {
            var link = binding.editTextText.text.toString()
            if (link.isEmpty()) {
                link = getLinkFromClipboard().toString()
                if (link == null) {
                    showToast("Clipboard rỗng vui lòng nhập link")
                    return@setOnClickListener
                }
                binding.editTextText.setText(link)
            }
            if (!isValidUrl(link)) {
                showToast("Link không hợp lệ")
                return@setOnClickListener
            }
            showVideoInfoBottomSheet(link)
        }
    }

    private fun getLinkFromClipboard(): String? {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (clipboard.hasPrimaryClip()) {
            val item = clipboard.primaryClip?.getItemAt(0)
            return item?.text.toString()
        }
        return null
    }

    private fun setupOpenTikTokButtonClickListener() {
        binding.btnOpentiktok.setOnClickListener {
            openTikTokApp()
        }
    }

    private fun openTikTokApp() {
        val tikTokPackageName = "com.ss.android.ugc.trill"
        val intent = packageManager.getLaunchIntentForPackage(tikTokPackageName)
        if (intent != null) {
            try {
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("OpenTikTok" ,"Error opening TikTok" ,e)
            }
        } else {
            openTikTokInPlayStore(tikTokPackageName)
        }
    }

    private fun openTikTokInPlayStore(tikTokPackageName: String) {
        val playStoreIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("market://details?id=$tikTokPackageName")
            setPackage("com.android.vending")
        }
        if (playStoreIntent.resolveActivity(packageManager) != null) {
            startActivity(playStoreIntent)
        } else {
            openTikTokInBrowser(tikTokPackageName)
        }
    }

    private fun openTikTokInBrowser(tikTokPackageName: String) {
        val browserIntent = Intent(
            Intent.ACTION_VIEW ,
            Uri.parse("https://play.google.com/store/apps/details?id=$tikTokPackageName")
        )
        startActivity(browserIntent)
    }

    private fun setupStorageDownloadButtonClickListener() {
        binding.btnStoraDownload.setOnClickListener {
            val intent = Intent(this ,StorageActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupReviewButtonClickListener() {
        binding.btnXemLai.setOnClickListener {
            lastVideoInfo?.let { videoInfo ->
                showVideoInfoBottomSheet2(videoInfo)
            }
        }
    }

    private fun setupUsageButtonClickListener() {
        binding.btnCachSuDung.setOnClickListener {
            val intent = Intent(this ,UsingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showVideoInfoBottomSheet(link: String) {
        val progressDialog = ProgressDialog(this).apply {
            setMessage("Loading data...")
            setCancelable(false)
            show()
        }

        lifecycleScope.launch {
            try {
                val videoInfo = withContext(Dispatchers.IO) {
                    getTikTokVideoInfo(link)
                }

                progressDialog.dismiss()

                videoInfo?.let {
                    lastVideoInfo = it
                    val videoInfoBottomSheet = VideoInfoBottomSheetFragment.newInstance(it)
                    videoInfoBottomSheet.show(supportFragmentManager ,"VideoInfoBottomSheet")
                } ?: showToast("Link không hợp lệ hoặc không thể tải dữ liệu video")
            } catch (e: Exception) {
                progressDialog.dismiss()
                e.printStackTrace()
            }
        }
    }

    private fun showVideoInfoBottomSheet2(videoInfo: TikTokVideoInfo) {
        val videoInfoBottomSheet = VideoInfoBottomSheetFragment.newInstance(videoInfo)
        videoInfoBottomSheet.show(supportFragmentManager ,"VideoInfoBottomSheet")
    }
}