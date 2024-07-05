package com.thuc0502.tiktokdownloadwithoutwatermark.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.thuc0502.tiktokdownloadwithoutwatermark.Fragment.AudioFragment
import com.thuc0502.tiktokdownloadwithoutwatermark.Fragment.VideoFragment

class StoragePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return if (position == 0) VideoFragment() else AudioFragment()
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0) "Videos" else "Audios"
    }
}