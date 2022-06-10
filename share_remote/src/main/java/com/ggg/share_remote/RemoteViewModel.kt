package com.ggg.share_remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext


/**
 * Created by  gggao on 6/9/2022.
 */
class RemoteViewModel : ViewModel() {
    private val repository = RemoteRepository()

    fun fetch(): Flow<RemoteBean> {
        return flow {
            withContext(viewModelScope.coroutineContext + Dispatchers.Default) {
                repository.fetchFiles("").apply {
                    when (this) {
                        is ResponseResult.Failed -> RemoteBean(false)
                        is ResponseResult.Success -> RemoteBean(true, this.files)
                    }

                }

            }
        }

    }
}