package com.thuc0502.tiktokdownloadwithoutwatermark.Adapter

import android.content.Intent
import android.graphics.Color
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.thuc0502.tiktokdownloadwithoutwatermark.Activity.VideoPlayerActivity
import com.thuc0502.tiktokdownloadwithoutwatermark.R
import com.thuc0502.tiktokdownloadwithoutwatermark.databinding.ItemVideoBinding
import java.io.File

class VideoAdapter(
    private var files: Array<File>,
    private val ivNoData: ImageView,
    private val tvNoData: TextView,
    private val videoRecyclerView: RecyclerView,
    private val layout: RelativeLayout,
) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {
    private lateinit var binding: ItemVideoBinding
    private var currentPopupMenu: PopupMenu? = null

    inner class ViewHolder(
        view: View,
    ) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        binding = ItemVideoBinding.bind(holder.itemView)
        binding.txtName.text = files[position].name
        binding.imageView.setImageBitmap(
            ThumbnailUtils.createVideoThumbnail(
                files[position].absolutePath,
                MediaStore.Video.Thumbnails.MINI_KIND,
            ),
        )

        setOnClickListeners(holder, position)
    }

    private fun setOnClickListeners(
        holder: ViewHolder,
        position: Int,
    ) {
        with(binding) {
            imageView.setOnClickListener { startVideoPlayerActivity(holder, position) }
            menuIcon.setOnClickListener { view -> showPopupMenu(view, holder, position) }
        }
    }

    private fun startVideoPlayerActivity(
        holder: ViewHolder,
        position: Int,
    ) {
        val videoPath = files[position].absolutePath
        val intent =
            Intent(holder.itemView.context, VideoPlayerActivity::class.java).apply {
                putExtra("VIDEO_PATH", videoPath)
            }
        holder.itemView.context.startActivity(intent)
    }

    private fun showPopupMenu(
        view: View,
        holder: ViewHolder,
        position: Int,
    ) {
        currentPopupMenu?.dismiss()
        val popup = PopupMenu(view.context, view)
        popup.menuInflater.inflate(R.menu.menu, popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_play -> startVideoPlayerActivity(holder, position)
                R.id.action_share -> shareVideo(holder, position)
                R.id.action_delete -> deleteFile(holder, position)
            }
            true
        }
        popup.show()
    }

    private fun shareVideo(
        holder: ViewHolder,
        position: Int,
    ) {
        val videoPath = files[position].absolutePath
        val videoFile = File(videoPath)
        val videoUri =
            FileProvider.getUriForFile(
                holder.itemView.context,
                "${holder.itemView.context.packageName}.provider",
                videoFile,
            )

        val shareIntent =
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, videoUri)
                type = "video/*"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        holder.itemView.context.startActivity(
            Intent.createChooser(shareIntent, "Chia sẻ video"),
        )
    }

    private fun deleteFile(
        holder: ViewHolder,
        position: Int,
    ) {
        val file = files[position]
        when {
            file.delete() -> {
                files = files.filterIndexed { index, _ -> index != position }.toTypedArray()
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, files.size)
                if (files.isEmpty()) {
                    ivNoData.visibility = View.VISIBLE
                    tvNoData.visibility = View.VISIBLE
                    videoRecyclerView.visibility = View.GONE
                    layout.setBackgroundColor(Color.GRAY)
                }
            }

            else -> Log.e("VideoAdapter", "Cannot delete file: ${file.absolutePath}")
        }
    }

    fun dismissPopupMenu() {
        currentPopupMenu?.dismiss()
    }

    override fun getItemCount() = files.size
}
