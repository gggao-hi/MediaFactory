package com.ggg.video.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ggg.mediafactory.ui.theme.MediaFactoryTheme
import com.ggg.video.player.VideoPlayer
import com.ggg.video.viewmodel.VideoSourceViewModel
import kotlinx.coroutines.flow.Flow

class VideoActivity : ComponentActivity() {
    private val viewModel = VideoSourceViewModel()
    private val player = VideoPlayer(this)
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
            val path: String by video.collectAsState(initial = "")
            Column(modifier = Modifier.verticalScroll(ScrollState(0))) {
                player.InitPlayer(300.dp, path)
            }
        }
    }

}