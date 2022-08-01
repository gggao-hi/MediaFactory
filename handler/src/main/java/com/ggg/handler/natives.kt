package com.ggg.handler

import android.graphics.Bitmap
import android.view.Surface
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

    external fun  initVideoHandler( surfaceView: Surface?)

    fun exeCommand(
        command: VideoCommand,
        surfaceView: Surface?,
        listener: Function1<Int, Unit>
    ) {
        val resultListener = object : OnHandlerResultListener {
            override fun onResult(code: Int) {
                listener.invoke(code)
            }

        }
        sendVideoCommand(command, surfaceView, resultListener)
    }

     external fun sendVideoCommand(
        command: VideoCommand,
        surfaceView: Surface?,
        resultListener: OnHandlerResultListener
    )

    sealed class VideoCommand(val type: Int, val paths: Map<String, String>)

    data class SplitVideoCommand(val path: String) :
        VideoCommand(101, mutableMapOf<String, String>().apply { put("videoPath", path) })

    data class DecodeCommand(val videoPath: String?) :
        VideoCommand(103, mutableMapOf<String, String>().apply {
            videoPath?.let { put("videoPath", it) }
        })

    interface OnHandlerResultListener {
        fun onResult(code: Int)
    }
}

