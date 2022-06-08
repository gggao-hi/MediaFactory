package com.ggg.handler.video.viewmodel

import android.os.Environment
import com.ggg.base.BaseApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.io.FileOutputStream


/**
 * Created by  gggao on 1/14/2022.
 */
class VideoSourceViewModel {
    private var path: String? = null

    fun getVideoPath(): Flow<String> {
        return flow {
            if (path.isNullOrEmpty()) {
                BaseApplication.getCurrentActivity()?.let { it ->
                    it.assets.open("test.mp4").apply {
                        val file = it.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                        file?.let { file ->
                            if (!file.exists()) {
                                file.mkdirs()
                            }
                            val filePath = "${file.absolutePath}${File.separator}test.mp4"
                            val fileOut =
                                FileOutputStream(filePath)
                            try {
                                fileOut.write(readBytes())
                                fileOut.flush()
                                path = filePath
                            } catch (e: Exception) {
                                e.printStackTrace()

                            } finally {
                                fileOut.close()
                                close()
                            }

                        }

                    }
                }

            }
            emit(path!!)
        }.flowOn(Dispatchers.IO)
    }
}