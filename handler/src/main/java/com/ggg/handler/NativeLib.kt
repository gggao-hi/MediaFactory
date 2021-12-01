package com.ggg.handler

object NativeLib {
    init {
        System.loadLibrary("handler")
    }

    external fun changeImageGray(buff: IntArray, width: Int, height: Int): IntArray
}