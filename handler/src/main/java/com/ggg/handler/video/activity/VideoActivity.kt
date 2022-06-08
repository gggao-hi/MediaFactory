package com.ggg.handler.video.activity

import android.os.Bundle
import android.view.Gravity
import android.view.Surface
import android.view.SurfaceView
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ggg.handler.MediaHandler
import com.ggg.base.ui.theme.MediaFactoryTheme
import com.ggg.handler.video.player.VideoPlayer
import com.ggg.handler.video.viewmodel.VideoSourceViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class VideoActivity : ComponentActivity() {
    private val viewModel = VideoSourceViewModel()
    private val player = VideoPlayer(this)

    private var currentSurface: Surface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    @Composable
    private fun Content() {

        val video: Flow<String> by remember {
            mutableStateOf(viewModel.getVideoPath())
        }

        MediaFactoryTheme(title = "${intent.getStringExtra("title")}") {
            Column(modifier = Modifier.verticalScroll(ScrollState(0))) {
                player.InitPlayer(300.dp, video)
                Spacer(modifier = Modifier.height(20.dp))
                decoderButton()
                Spacer(modifier = Modifier.height(20.dp))
                AndroidView(
                    factory = {
                        FrameLayout(it).apply {
                            layoutParams =
                                FrameLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                            addView(SurfaceView(it).apply {
                                layoutParams =
                                    FrameLayout.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT
                                    )
                                currentSurface = holder.surface

                            })
                        }
                    },
                    Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }
        }

    }

    @Composable
    private fun decoderButton() {
        var showLoading by remember {
            mutableStateOf(false)
        }
        Button(onClick = {
            showLoading = true
            val coroutineScope = CoroutineScope(Dispatchers.IO)
            coroutineScope.launch {
                MediaHandler.exeCommand(
                    MediaHandler.DecodeCommand(
                        player.currentVideoPath,
                    ), currentSurface
                ) {
                    val text = if (it == 0) {
                        "decode success"
                    } else {
                        "decode failed"
                    }
                    coroutineScope.launch(Dispatchers.Main) {
                        showLoading = false
                        Toast.makeText(
                            this@VideoActivity, text, Toast.LENGTH_LONG
                        ).apply {
                            setGravity(Gravity.CENTER, 0, 0)
                        }.show()
                    }
                }
            }


        }) {
            Text(text = "decoder")
        }
//        if (showLoading) {
//            dialog()
//        }
    }

    @Composable
    private fun dialog() {
        Dialog(
            onDismissRequest = {},
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(modifier = Modifier.size(300.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

    }
}