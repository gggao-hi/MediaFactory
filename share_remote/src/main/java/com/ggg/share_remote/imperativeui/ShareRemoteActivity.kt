package com.ggg.share_remote.imperativeui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ggg.share_remote.*
import com.ggg.share_remote.databinding.LayoutShareRemoteBinding

class ShareRemoteActivity : AppCompatActivity() {
    private val fileItems: MutableList<FileItem> = mutableListOf()
    private val titleItems: MutableList<FileItem> = mutableListOf()
    private val fileItemAdapter by lazy {
        FolderItemAdapter(fileItems)
    }
    private val titleItemAdapter by lazy {
        TitleItemAdapter(titleItems)
    }
    private var viewModel: RemoteViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = LayoutShareRemoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(RemoteViewModel::class.java)
        initToolbar()
        initItems(binding)
        initData()
    }

    private fun initToolbar() {
        supportActionBar?.title = intent.getStringExtra("title") ?: ""
    }

    private fun initItems(binding: LayoutShareRemoteBinding) {
        binding.rvFoldersTitle.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.rvFoldersTitle.adapter = titleItemAdapter
        titleItemAdapter.onItemClick = {
            onclick(it)
        }
        binding.rvFoldersItem.layoutManager = GridLayoutManager(this, 4)
        binding.rvFoldersItem.adapter = fileItemAdapter
        fileItemAdapter.onItemClick = {
            onclick(it)
        }
        viewModel?.remoteBeanLiveData?.observe(this) {
            if (it.isSuccess) {
                fileItems.clear()
                titleItems.clear()
                it.fileItems?.apply {
                    fileItems.addAll(this)
                    fileItemAdapter.notifyDataSetChanged()
                }
                it.folders?.apply {
                    titleItems.addAll(this)
                    titleItemAdapter.notifyDataSetChanged()
                }
            }

        }
    }

    private fun initData() {
        viewModel?.fetch("")
    }

    private fun onclick(item: FileItem) {
        if (item.type is FileType.FOLDER) {
            viewModel?.fetch(item.id)
        }
    }
}