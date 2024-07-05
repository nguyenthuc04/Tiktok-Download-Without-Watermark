package com.thuc0502.tiktokdownloadwithoutwatermark.Fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.thuc0502.tiktokdownloadwithoutwatermark.Adapter.VideoAdapter
import com.thuc0502.tiktokdownloadwithoutwatermark.databinding.FragmentVideoBinding
import java.io.File

class VideoFragment : Fragment() {
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var binding: FragmentVideoBinding
    override fun onCreateView(
        inflater: LayoutInflater ,
        container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(inflater ,container ,false)
        return binding.root
    }

    override fun onViewCreated(view: View ,savedInstanceState: Bundle?) {
        super.onViewCreated(view ,savedInstanceState)
        binding = FragmentVideoBinding.bind(view)
        val videos = getDownloadedVideos()

        if (videos.isEmpty()) {
            binding.ivNoData.visibility = View.VISIBLE
            binding.tvNoData.visibility = View.VISIBLE
            binding.videoRecyclerView.visibility = View.GONE
            binding.layout.setBackgroundColor(Color.GRAY)
        } else {
            binding.ivNoData.visibility = View.GONE
            binding.tvNoData.visibility = View.GONE
            binding.videoRecyclerView.visibility = View.VISIBLE

            binding.layout.setBackgroundColor(Color.WHITE)

            // Hiển thị danh sách video
            videoAdapter = VideoAdapter(
                videos ,
                binding.ivNoData ,
                binding.tvNoData ,
                binding.videoRecyclerView ,
                binding.layout
            )
            binding.videoRecyclerView.layoutManager = GridLayoutManager(context ,2)
            binding.videoRecyclerView.adapter = videoAdapter
        }
    }

    private fun getDownloadedVideos(): Array<File> {
        val videoDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) ,
            "TikMate/Video"
        )
        return videoDir.listFiles() ?: emptyArray()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::videoAdapter.isInitialized) {
            videoAdapter.dismissPopupMenu()
        }

    }
}