package com.ggg.handler

import android.graphics.Bitmap
import androidx.annotation.IntDef

object MediaHandler {
    init {
        System.loadLibrary("handler")


    }

    external fun negative(bitmap: Bitmap?): Int

    external fun flip(bitmap: Bitmap?, @FlipDirection direction: Int): Int

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(FlipDirection.HORIZONTAL, FlipDirection.VERTICAL)
    annotation class FlipDirection {
        companion object {
            const val VERTICAL = 1
            const val HORIZONTAL = 2
        }
    }

    external fun sendVideoCommand(command: VideoCommand): Int

    sealed class VideoCommand(val type: Int, val paths: Map<String, String>)

    data class SplitVideoCommand(val path: String) :
        VideoCommand(101, mutableMapOf<String, String>().apply { put("videoPath", path) })

    data class DecodeCommand(val videoPath: String?, val outPath: String?) :
        VideoCommand(103, mutableMapOf<String, String>().apply {
            videoPath?.let { put("videoPath", it) }
            outPath?.let { put("outPath", it) }

        })
}

