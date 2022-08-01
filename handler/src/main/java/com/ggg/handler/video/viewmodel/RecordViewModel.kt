package com.ggg.handler.video.viewmodel

import android.view.Surface
import androidx.lifecycle.ViewModel
import com.ggg.handler.MediaHandler


/**
 * Created by  gggao on 1/14/2022.
 */
class RecordViewModel : ViewModel() {

    fun loadVideo(surface: Surface?) {
        MediaHandler.initVideoHandler(surface)
    }
}