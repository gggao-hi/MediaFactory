package com.ggg.handler

class NativeLib {

    /**
     * A native method that is implemented by the 'handler' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'handler' library on application startup.
        init {
            System.loadLibrary("handler")
        }
    }
}