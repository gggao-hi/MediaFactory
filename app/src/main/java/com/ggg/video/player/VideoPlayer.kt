package com.ggg.video.player

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.LinearLayout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.viewinterop.AndroidView


/**
 * Created by  gggao on 1/10/2022.
 */
class VideoPlayer(private val context: Context) {
    private var surfaceHolder: SurfaceHolder? = null
    private val mediaPlayer = MediaPlayer().apply {
        val attributes = AudioAttributes.Builder()
            .setLegacyStreamType(AudioManager.STREAM_MUSIC)
            .build()
        setAudioAttributes(attributes)
    }

    fun resetVideoPath(path: String) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(path)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    @Composable
    fun InitPlayer(height: Dp, videoPath: String) {
        AndroidView(
            factory = {
                return@AndroidView LinearLayout(it).apply {
                    layoutParams =
                        LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                        )

                    addView(SurfaceView(it).apply {
                        layoutParams =
                            LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                            )
                        surfaceHolder = holder
                        holder.addCallback(object : SurfaceHolder.Callback {
                            override fun surfaceCreated(holder: SurfaceHolder) {
                                play(videoPath)
                            }

                            override fun surfaceChanged(
                                holder: SurfaceHolder,
                                format: Int,
                                width: Int,
                                height: Int
                            ) {

                            }

                            override fun surfaceDestroyed(holder: SurfaceHolder) {
                                mediaPlayer.stop()
                                mediaPlayer.release()
                            }

                        })
                    })
                }


            }, modifier = Modifier
                .fillMaxWidth()
                .height(height)
        )
    }

    private fun play(videoPath: String) {
        mediaPlayer.setDisplay(surfaceHolder)
        if (videoPath.isNotEmpty()) {
            mediaPlayer.setDataSource(videoPath)
            mediaPlayer.prepare()
            mediaPlayer.start()
        }

    }
}