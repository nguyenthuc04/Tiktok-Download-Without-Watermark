package com.thuc0502.tiktokdownloadwithoutwatermark.Adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.thuc0502.tiktokdownloadwithoutwatermark.Fragment.AudioFragment
import com.thuc0502.tiktokdownloadwithoutwatermark.Fragment.ImageFragment
import com.thuc0502.tiktokdownloadwithoutwatermark.Fragment.VideoFragment

class StoragePagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> VideoFragment()
            1 -> AudioFragment()
            else -> ImageFragment()
        }
    }
}