package com.thuc0502.tiktokdownloadwithoutwatermark.Fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
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
import java.util.concurrent.atomic.AtomicBoolean

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
            setupDownloadButton(btnDowLoadVideo, "Tải xuống video (${videoInfo.size})", videoInfo.playUrl, ::downloadVideo,videoInfo.size)
            setupDownloadButton(btnDownloadSound, "Tải nhạc xuống (${videoInfo.musicSize})", videoInfo.music, ::downloadSound,videoInfo.size)
            btnClose.setOnClickListener { dismiss() }
        }
    }
    private fun setupDownloadButton(button: Button ,text: String ,url: String ,downloadFunction: (String,String) -> Unit,data:String) {
        button.apply {
            this.text = text
            setOnClickListener {
                checkPermissionAndDownload(data,url, downloadFunction)
            }
        }
    }
    private fun checkPermissionAndDownload(data: String ,url: String, downloadFunction: (String,String) -> Unit) {
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET)

        if (permissions.all { permission -> ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED }) {
            downloadFunction(data,url)
        } else {
            ActivityCompat.requestPermissions(requireActivity(), permissions, 0)
        }
    }

    private fun downloadSound(data: String,url: String) {
        downloadFile(data,url, "TikMate/Sound", ".mp3") { intent ->
            intent.putExtra("tab", "Audios")
            startActivity(intent)
        }
    }

    private fun downloadVideo(data: String,url: String) {
        downloadFile(data,url, "TikMate/Video", ".mp4") { intent ->
            intent.putExtra("tab", "Videos")
            startActivity(intent)
        }
    }

    private suspend fun downloadData(
        client: OkHttpClient ,
        request: Request ,
        file: File ,
        progressBar: ProgressBar ,
        txtData1: TextView ,
        isCancelled: AtomicBoolean
    ) {
        val response = client.newCall(request).execute()

        if (response.isSuccessful) {
            val inputStream = response.body?.byteStream()
            val outputStream = withContext(Dispatchers.IO) {
                FileOutputStream(file)
            }

            val buffer = ByteArray(2048)
            var bytesRead: Int
            var totalBytesRead: Long = 0
            val contentLength = response.body?.contentLength() ?: 0

            while (withContext(Dispatchers.IO) {
                    inputStream?.read(buffer).also { bytesRead = it ?: -1 }
                } != -1) {
                if (isCancelled.get()) {
                    file.delete() // Delete the partially downloaded file
                    break
                }

                totalBytesRead += bytesRead
                val progress = (totalBytesRead * 100 / contentLength).toInt()
                withContext(Dispatchers.Main) {
                    progressBar.progress = progress
                    // Update the txtData1 text view
                    val downloadedMB = totalBytesRead.toFloat() / (1024 * 1024)
                    val downloadedKB = totalBytesRead.toFloat() / 1024
                    txtData1.text = if (downloadedMB < 1) {
                        String.format("%.1f KB", downloadedKB)
                    } else {
                        String.format("%.1f MB", downloadedMB)
                    }
                }
                withContext(Dispatchers.IO) {
                    outputStream.write(buffer ,0 ,bytesRead)
                }
            }
            withContext(Dispatchers.IO) {
                outputStream.close()
            }
        } else {
            throw IOException("Failed to download file: $response")
        }
    }

    private fun downloadFile(data: String, url: String, directory: String, extension: String, onSuccess: (Intent) -> Unit) {
        val isCancelled = AtomicBoolean(false)
        lifecycleScope.launch {
            val dialog = Dialog(requireContext()).apply {
                setContentView(R.layout.custom_progress_dialog_download)
                setCancelable(false)
                window?.setBackgroundDrawableResource(R.drawable.progress_dialog)
                window?.setLayout(600, 400)
                val btnCancel = findViewById<Button>(R.id.btnCancel)
                btnCancel.setOnClickListener {
                    isCancelled.set(true)
                }
            }
            dialog.show()
            val progressBar = dialog.findViewById<ProgressBar>(R.id.progressBar3)
            val txtData1 = dialog.findViewById<TextView>(R.id.txtData1)
            val txtData2 = dialog.findViewById<TextView>(R.id.txtData2)
            txtData2.text = data
            try {
                withContext(Dispatchers.IO) {
                    val client = OkHttpClient()
                    val request = Request.Builder().url(url).build()
                    val dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), directory)
                    dir.mkdirs()
                    val file = File(dir, generateRandomFileName(extension))

                    downloadData(client ,request ,file ,progressBar ,txtData1 ,isCancelled)

                    withContext(Dispatchers.Main) {
                        if (!isCancelled.get()) {
                            dialog.dismiss()
                            dismiss() // Close BottomSheetDialogFragment
                            // Navigate to StorageActivity and open the corresponding tab
                            val intent = Intent(requireContext(), StorageActivity::class.java)
                            onSuccess(intent)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("DownloadFile", "Error downloading file", e)
                e.printStackTrace()
            } finally {
                if (isCancelled.get()) {
                    dialog.dismiss()
                }
            }
        }
    }
}
