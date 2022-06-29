package com.ggg.plugins_loader

import android.content.Context
import android.os.Environment
import android.util.Log
import com.ggg.plugins_loader.plugins.TestPluginLoader
import java.io.File
import java.io.FileOutputStream


/**
 * Created by  gggao on 6/28/2022.
 */
object PluginsLoader {

    private const val DEX_PATH_PREFIX = "plugins/"
    private var basePath: String? = null

    fun init(context: Context) {
        basePath =
            "${context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath}${File.separator}$DEX_PATH_PREFIX"
        Log.d("xxx", "plugins path:$basePath")
        mockAPK(context)
        loadPlugins(FunctionPlugin.Test(), context)
    }

    private fun mockAPK(context: Context) {
        val file = File(basePath!!)
        if (!file.exists()) {
            file.mkdirs()
        }
        context.assets?.open("test.apk")?.apply {
            val f = File("${basePath}test.apk")
            f.let { file ->
                if (!file.exists()) {
                    file.createNewFile()
                }
                val filePath = f.absolutePath
                val fileOut =
                    FileOutputStream(filePath)
                try {
                    fileOut.write(readBytes())
                    fileOut.flush()
                } catch (e: Exception) {
                    e.printStackTrace()

                } finally {
                    fileOut.close()
                    close()
                }

            }

        }
    }

    private fun loadPlugins(plugin: FunctionPlugin, context: Context) {
        when (plugin) {
            is FunctionPlugin.Test -> loadTestPlugin(plugin.pluginName, context)
        }
    }

    private fun loadTestPlugin(name: String, context: Context) {
        val path = "$basePath$name"
        TestPluginLoader.load(path, context)

    }

    sealed class FunctionPlugin(val pluginName: String) {
        class Test : FunctionPlugin("test.apk")
    }

}