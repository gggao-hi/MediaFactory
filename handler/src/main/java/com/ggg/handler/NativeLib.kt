package com.ggg.handler

object NativeLib {
    init {
        System.loadLibrary("handler")
    }

    external fun changeImageGray(buff: ByteArray, width: Int, height: Int): ByteArray
}