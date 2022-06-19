package com.ggg.share_remote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ggg.share_remote.databinding.LayoutShareRemoteBinding

class ShareRemoteActivity : AppCompatActivity() {
    private val fileItems: MutableList<FileItem> = mutableListOf()
    private val fileItemAdapter by lazy {
        FolderItemAdapter(fileItems)
    }
    private var viewModel: RemoteViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = LayoutShareRemoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(RemoteViewModel::class.java)
        initToolbar()
        initItems(binding)
    }

    private fun initToolbar() {
        supportActionBar?.title=intent.getStringExtra("title")?:""

    }

    private fun initItems(binding: LayoutShareRemoteBinding) {
        binding.rvFoldersItem.layoutManager = GridLayoutManager(this, 4)
        binding.rvFoldersItem.adapter = fileItemAdapter
        viewModel?.remoteBeanLiveData?.observe(this) {
            if (it.isSuccess) {
                fileItems.clear()
                it.fileItems?.apply {
                    fileItems.addAll(this)
                }

            }

        }
    }
}