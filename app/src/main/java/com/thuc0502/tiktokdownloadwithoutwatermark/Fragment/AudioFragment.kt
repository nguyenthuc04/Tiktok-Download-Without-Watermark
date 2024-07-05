package com.thuc0502.tiktokdownloadwithoutwatermark.Fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thuc0502.tiktokdownloadwithoutwatermark.Adapter.AudioAdapter
import com.thuc0502.tiktokdownloadwithoutwatermark.databinding.FragmentAudioBinding
import java.io.File

class AudioFragment : Fragment() {
    private lateinit var audioAdapter: AudioAdapter
    private lateinit var binding: FragmentAudioBinding
    override fun onCreateView(
        inflater: LayoutInflater ,
        container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAudioBinding.inflate(inflater ,container ,false)
        return binding.root
    }

    override fun onViewCreated(view: View ,savedInstanceState: Bundle?) {
        super.onViewCreated(view ,savedInstanceState)
        binding = FragmentAudioBinding.bind(view)

        val audios = getDownloadedAudios()
        if (audios.isEmpty()) {
            binding.ivNoData.visibility = View.VISIBLE
            binding.tvNoData.visibility = View.VISIBLE
            binding.audioRecyclerView.visibility = View.GONE
            binding.layout.setBackgroundColor(Color.GRAY)
        } else {
            binding.ivNoData.visibility = View.GONE
            binding.tvNoData.visibility = View.GONE
            binding.audioRecyclerView.visibility = View.VISIBLE

            binding.layout.setBackgroundColor(Color.WHITE)

            audioAdapter = AudioAdapter(
                audios ,
                binding.ivNoData ,
                binding.tvNoData ,
                binding.audioRecyclerView ,
                binding.layout
            )
            binding.audioRecyclerView.adapter = audioAdapter
        }
    }

    private fun getDownloadedAudios(): Array<File> {
        val audioDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) ,
            "TikMate/Sound"
        )
        return audioDir.listFiles() ?: emptyArray()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::audioAdapter.isInitialized) {
            audioAdapter.dismissPopupMenu()
        }
    }
}