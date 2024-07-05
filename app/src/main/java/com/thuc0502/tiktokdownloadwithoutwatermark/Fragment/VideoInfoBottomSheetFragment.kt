package com.thuc0502.tiktokdownloadwithoutwatermark.Fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thuc0502.tiktokdownloadwithoutwatermark.Activity.StorageActivity
import com.thuc0502.tiktokdownloadwithoutwatermark.Model.TikTokVideoInfo
import com.thuc0502.tiktokdownloadwithoutwatermark.R
import com.thuc0502.tiktokdownloadwithoutwatermark.databinding.FragmentVideoInfoBottomSheetBinding
import com.thuc0502.tiktokdownloadwithoutwatermark.generateRandomFileName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class VideoInfoBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentVideoInfoBottomSheetBinding
    private lateinit var videoInfo: TikTokVideoInfo
    companion object {
            fun newInstance(videoInfo: TikTokVideoInfo): VideoInfoBottomSheetFragment {
                val fragment = VideoInfoBottomSheetFragment()
                fragment.videoInfo = videoInfo
                return fragment
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater ,
        container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoInfoBottomSheetBinding.inflate(inflater, container, false)
        dialog?.setCancelable(false)
        return binding.root
    }
    override fun getTheme(): Int {
        return R.style.Theme_Material3BottomSheetDialog
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View ,savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sử dụng videoInfo đã được truyền vào để cập nhật giao diện người dùng
        binding.tvNickName.text = videoInfo.nickname
        binding.tvTitle.text = videoInfo.title

        Glide.with(requireContext())
            .load(videoInfo.authorAvatar)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.imgAvatar)

        setOnClickListener()
    }

    private fun setOnClickListener() {
        with(binding) {
            setupDownloadButton(btnDowLoadVideo, "Tải xuống video (${videoInfo.size})", videoInfo.playUrl, ::downloadVideo)
            setupDownloadButton(btnDownloadSound, "Tải nhạc xuống (${videoInfo.musicSize})", videoInfo.music, ::downloadSound)
            btnClose.setOnClickListener { dismiss() }
        }
    }
    private fun setupDownloadButton(button: Button ,text: String ,url: String ,downloadFunction: (String) -> Unit) {
        button.apply {
            this.text = text
            setOnClickListener {
                checkPermissionAndDownload(url, downloadFunction)
            }
        }
    }
    private fun checkPermissionAndDownload(url: String, downloadFunction: (String) -> Unit) {
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET)

        if (permissions.all { permission -> ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED }) {
            downloadFunction(url)
        } else {
            ActivityCompat.requestPermissions(requireActivity(), permissions, 0)
        }
    }

    private fun downloadSound(url: String) {
        downloadFile(url, "TikMate/Sound", ".mp3") { intent ->
            intent.putExtra("tab", "Audios")
            startActivity(intent)
        }
    }

    private fun downloadVideo(url: String) {
        downloadFile(url, "TikMate/Video", ".mp4") { intent ->
            intent.putExtra("tab", "Videos")
            startActivity(intent)
        }
    }

    private fun downloadFile(url: String, directory: String, extension: String, onSuccess: (Intent) -> Unit) {
        lifecycleScope.launch {
            val progressDialog = ProgressDialog(requireContext()).apply {
                max = 100
                setMessage("Downloading...")
                setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                show()
            }

            try {
                withContext(Dispatchers.IO) {
                    val client = OkHttpClient()
                    val request = Request.Builder().url(url).build()
                    val response = client.newCall(request).execute()

                    if (response.isSuccessful) {
                        val inputStream = response.body?.byteStream()
                        val dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), directory)
                        dir.mkdirs()
                        val file = File(dir, generateRandomFileName(extension))
                        val outputStream = FileOutputStream(file)

                        val buffer = ByteArray(2048)
                        var bytesRead: Int
                        var totalBytesRead: Long = 0
                        val contentLength = response.body?.contentLength() ?: 0

                        while (inputStream?.read(buffer).also { bytesRead = it ?: -1 } != -1) {
                            totalBytesRead += bytesRead
                            val progress = (totalBytesRead * 100 / contentLength).toInt()
                            withContext(Dispatchers.Main) {
                                progressDialog.progress = progress
                            }
                            outputStream.write(buffer, 0, bytesRead)
                        }
                        outputStream.close()
                        withContext(Dispatchers.Main) {
                            progressDialog.dismiss()
                            dismiss() // Đóng BottomSheetDialogFragment
                            // Chuyển đến StorageActivity và mở tab tương ứng
                            val intent = Intent(requireContext(), StorageActivity::class.java)
                            onSuccess(intent)
                        }
                    } else {
                        throw IOException("Failed to download file: $response")
                    }
                }
            } catch (e: Exception) {
                Log.e("DownloadFile", "Error downloading file", e)
                e.printStackTrace()
            } finally {
                progressDialog.dismiss()
            }
        }
    }
}
