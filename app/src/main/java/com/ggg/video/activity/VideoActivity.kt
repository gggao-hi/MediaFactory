package com.ggg.video.activity

import android.os.Bundle
import android.view.SurfaceView
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.ggg.mediafactory.ui.theme.MediaFactoryTheme

class VideoActivity : ComponentActivity() {
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
                AndroidView(factory = {
                    return@AndroidView LinearLayout(it).apply {
                        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
                        layoutParams.height = 100
                        addView(SurfaceView(it).apply {
                            layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
                            layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT

                        })
                    }


                })
            }
        }
    }

}