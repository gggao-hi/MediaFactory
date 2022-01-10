package com.ggg.video.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ggg.mediafactory.ui.theme.MediaFactoryTheme
import com.ggg.video.player.VideoPlayer

class VideoActivity : ComponentActivity() {
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
            Column(modifier = Modifier.verticalScroll(ScrollState(0))) {
                player.InitPlayer(300.dp, "")
            }
        }
    }

}