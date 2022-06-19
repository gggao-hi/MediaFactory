package com.ggg.share_remote

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


/**
 * Created by  gggao on 6/9/2022.
 */
class RemoteViewModel : ViewModel() {
    private val repository = RemoteRepository()
    val remoteBeanLiveData: MutableLiveData<RemoteBean> = MutableLiveData()
    fun fetch(id: String) {
        viewModelScope.launch {
            repository.fetchFiles("").apply {
                when (this) {
                    is ResponseResult.Failed -> remoteBeanLiveData.value = (RemoteBean(false))
                    is ResponseResult.Success -> remoteBeanLiveData.value =
                        (RemoteBean(true, this.files))
                }

            }
        }

    }
}