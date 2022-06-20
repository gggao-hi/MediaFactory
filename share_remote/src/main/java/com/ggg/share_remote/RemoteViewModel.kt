package com.ggg.share_remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


/**
 * Created by  gggao on 6/9/2022.
 */
class RemoteViewModel : ViewModel() {
    private val repository = RemoteRepository()
    val remoteBeanLiveData: MutableLiveData<RemoteBean> = MutableLiveData()
    private val title = mutableListOf<FileItem>()
    fun fetch(id: String?) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.fetchFiles("").apply {
                when (this) {
                    is ResponseResult.Failed -> setData(RemoteBean(false))
                    is ResponseResult.Success -> setData(
                        RemoteBean(
                            true,
                            this.files,
                            parseTitle(id, this.files)
                        )
                    )
                }
            }

        }
    }

    private suspend fun setData(bean: RemoteBean) {
        withContext(Dispatchers.Main) {
            remoteBeanLiveData.value = bean
        }
    }

    private fun parseTitle(id: String?, folders: List<FileItem>): List<FileItem> {
        if (id.isNullOrEmpty()) {
            title.clear()
            title.add(FileItem("/", "", FileType.FOLDER()))
        } else {
            var curIndex = -1
            run loop@{
                title.forEachIndexed { index, fileItem ->
                    if (fileItem.id == id) {
                        curIndex = index
                        return@loop
                    }
                    Log.d("xxx", "index:$index")
                }
            }
            if (curIndex != -1) {
                val temp = (title.take(curIndex + 1))
                title.clear()
                title.addAll(temp)

                curIndex = -1
            } else {
                run loop@{
                    folders.forEach {
                        if (it.id == id) {
                            title.add(it)
                            return@loop
                        }
                    }
                }
            }
        }

        return title
    }

}