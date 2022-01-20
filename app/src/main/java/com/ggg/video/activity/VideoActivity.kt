package com.ggg.video.activity

import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ggg.handler.MediaHandler
import com.ggg.mediafactory.ui.theme.MediaFactoryTheme
import com.ggg.video.player.VideoPlayer
import com.ggg.video.viewmodel.VideoSourceViewModel
import kotlinx.coroutines.flow.Flow
import java.io.File

class VideoActivity : ComponentActivity() {
    private val viewModel = VideoSourceViewModel()
    private val player = VideoPlayer(this)
    private val outPath: String by lazy {
        "${getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath}${File.separator}out.mp4"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    @Composable
    private fun Content() {
        MediaFactoryTheme(title = "${intent.getStringExtra("title")}") {
            val video: Flow<String> by remember {
                mutableStateOf(viewModel.getVideoPath())
            }

            Column(modifier = Modifier.verticalScroll(ScrollState(0))) {
                player.InitPlayer(300.dp, video)
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {

                    if (MediaHandler.sendVideoCommand(
                            MediaHandler.DecodeCommand(
                                player.currentVideoPath,
                                outPath
                            )
                        ) == 0
                    ) {
                        player.resetVideoPath(outPath)
                    }
                }) {
                    Text(text = "decoder")
                }
            }
        }
    }

}