package com.ggg.plugins_loader.plugins

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import com.ggg.base.plugin_interface.IPluginObserver
import dalvik.system.DexClassLoader


/**
 * Created by  gggao on 6/30/2022.
 */
object TestPluginLoader {
    private var dexClassLoader: DexClassLoader? = null
    fun load(path: String, context: Context) {
        dexClassLoader = DexClassLoader(
            path,
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath,
            null,
            context.classLoader.parent
        )
    }

    fun loadTestFragment(): Fragment? {
        return dexClassLoader?.loadClass("com.ggg.plugins_test.TestFragment")?.let {

            val instance = it.getConstructor(String::class.java).newInstance("")
            Log.d("xxx", "instance is IPluginObserver:${instance is IPluginObserver}")
            Log.d("xxx", "instance:${instance}")

            return if(instance is IPluginObserver){
                instance.getFragment()
            } else{
                null
            }
        }

    }
}