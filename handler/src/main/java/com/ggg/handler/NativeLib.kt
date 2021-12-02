package com.ggg.handler

import android.graphics.Bitmap

object NativeLib {
    init {
        System.loadLibrary("handler")
    }

    external fun negative(bitmap: Bitmap): Int
}