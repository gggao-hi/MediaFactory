package com.ggg.share_remote.imperativeui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ggg.share_remote.FileItem
import com.ggg.share_remote.databinding.ItemFoldersBinding

class FolderItemAdapter(private val data: List<FileItem>) :
    RecyclerView.Adapter<FolderItemAdapter.ItemHolder>() {
    var onItemClick: (item: FileItem) -> Unit = {}

    class ItemHolder(private val binding: ItemFoldersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FileItem) {
            binding.ivIcon.setImageResource(item.type.icon)
            binding.tvName.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding: ItemFoldersBinding =
            ItemFoldersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            onItemClick.invoke(data[position])
        }
    }

    override fun getItemCount(): Int = data.size
}