package com.thuc0502.tiktokdownloadwithoutwatermark.Activity

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.thuc0502.tiktokdownloadwithoutwatermark.Adapter.StoragePagerAdapter
import com.thuc0502.tiktokdownloadwithoutwatermark.R
import com.thuc0502.tiktokdownloadwithoutwatermark.databinding.ActivityStorageBinding


class StorageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = "Danh sách tải xuống"
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v ,insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left ,systemBars.top ,systemBars.right ,systemBars.bottom)
            insets
        }
        setupTabs()
        val tab = intent.getStringExtra("tab")
        if (tab != null) {
            val tabIndex = if (tab == "Videos") 0 else 1
            binding.viewPager.currentItem = tabIndex
        }
    }

    private fun setupTabs() {
        val adapter = StoragePagerAdapter(this)
        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 2

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.videos_tab_title)
                1 -> getString(R.string.audios_tab_title)
                else -> getString(R.string.images_tab_title)
            }
        }.attach()

        // Lắng nghe sự kiện chuyển đổi tab
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val fragment = adapter.createFragment(position)
                if (fragment is LoadableFragment) {
                    fragment.loadData()
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Respond to the action bar's Up/Home button
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}
interface LoadableFragment {
    fun loadData()
}