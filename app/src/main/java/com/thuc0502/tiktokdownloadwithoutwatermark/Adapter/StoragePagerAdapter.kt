package com.thuc0502.tiktokdownloadwithoutwatermark.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.thuc0502.tiktokdownloadwithoutwatermark.Fragment.AudioFragment
import com.thuc0502.tiktokdownloadwithoutwatermark.Fragment.ImageFragment
import com.thuc0502.tiktokdownloadwithoutwatermark.Fragment.VideoFragment

class StoragePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> VideoFragment()
            1 -> AudioFragment()
            else -> ImageFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Videos"
            1 -> "Audios"
            else -> "Images"
        }
    }
}