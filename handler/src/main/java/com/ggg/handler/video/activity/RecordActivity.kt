package com.ggg.handler.video.activity

import android.content.Context
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModelProvider
import com.ggg.handler.R
import com.ggg.handler.video.viewmodel.RecordViewModel

class RecordActivity : ComponentActivity() {
    private var viewModel: RecordViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecordViewModel::class.java)
        setContent {
            Content()
        }
    }

    @Preview
    @Composable
    private fun Content() {
        var isRecord: Boolean by remember {
            mutableStateOf(false)
        }
        Box(contentAlignment = Alignment.BottomCenter) {
            AndroidView(factory = {
                return@AndroidView loadSurfaceView(it)
            })
            Box(modifier = Modifier.padding(20.0.dp)) {
                Button(
                    modifier = Modifier
                        .width(100.0.dp)
                        .height(50.0.dp),
                    onClick = {
                        isRecord = !isRecord
                        if (isRecord) {
                            viewModel?.record()
                        } else {
                            viewModel?.stopRecord()
                        }
                    }) {
                    Text(
                        text = if (isRecord) getString(R.string.video_in_recording) else getString(R.string.vide_start_record)
                    )
                }

            }

        }

    }

    private fun loadSurfaceView(context: Context): View {
        return FrameLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            addView(SurfaceView(context).also { surfaceView ->
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
                surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
                    override fun surfaceCreated(holder: SurfaceHolder) {
                        viewModel?.loadVideo(holder.surface, surfaceView.width, surfaceView.height)
                    }

                    override fun surfaceChanged(
                        holder: SurfaceHolder,
                        format: Int,
                        width: Int,
                        height: Int
                    ) {
                    }

                    override fun surfaceDestroyed(holder: SurfaceHolder) {
                    }

                })
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.closeCamera()
    }
}