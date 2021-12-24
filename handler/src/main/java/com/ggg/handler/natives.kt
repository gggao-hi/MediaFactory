package com.ggg.handler

import android.graphics.Bitmap
import androidx.annotation.IntDef

object MediaHandler {
    init {
        System.loadLibrary("image_handler")

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

    data class SplitPathBean(val videoPath: String?, val soundPath: String?)
}

