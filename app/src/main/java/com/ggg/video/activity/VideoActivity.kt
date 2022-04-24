package com.ggg.video.activity

import android.os.Bundle
import android.os.Environment
import android.view.Gravity
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ggg.handler.MediaHandler
import com.ggg.mediafactory.ui.theme.MediaFactoryTheme
import com.ggg.video.player.VideoPlayer
import com.ggg.video.viewmodel.VideoSourceViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class VideoActivity : ComponentActivity() {
    private val viewModel = VideoSourceViewModel()
    private val player = VideoPlayer(this)
    private val outPath: String by lazy {
        "${getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath}"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    @Composable
    private fun Content() {
        var showLoading by remember {
            mutableStateOf(false)
        }
        val video: Flow<String> by remember {
            mutableStateOf(viewModel.getVideoPath())
        }

        MediaFactoryTheme(title = "${intent.getStringExtra("title")}") {
            Column(modifier = Modifier.verticalScroll(ScrollState(0))) {
                player.InitPlayer(300.dp, video)
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    showLoading = true
                    val coroutineScope = CoroutineScope(Dispatchers.IO)
                    coroutineScope.launch {
                        MediaHandler.sendVideoCommand(
                            MediaHandler.DecodeCommand(
                                player.currentVideoPath,
                                outPath
                            ), object : MediaHandler.OnHandlerResultListener {
                                override fun onResult(code: Int) {
                                    val text = if (code == 0) {
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
                        )
                    }


                }) {
                    Text(text = "decoder")
                }

                if (showLoading) {
                    dialog()
                }
            }
        }

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