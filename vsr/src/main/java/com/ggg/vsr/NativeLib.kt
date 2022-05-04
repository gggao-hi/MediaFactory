package com.ggg.vsr

class NativeLib {

    /**
     * A native method that is implemented by the 'vsr' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'vsr' library on application startup.
        init {
            System.loadLibrary("vsr")
        }
    }
}