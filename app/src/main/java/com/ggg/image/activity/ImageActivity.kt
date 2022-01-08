package com.ggg.image.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ggg.handler.MediaHandler
import com.ggg.mediafactory.R
import com.ggg.mediafactory.ui.theme.MediaFactoryTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class ImageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    private fun loadImage(): Flow<Bitmap> {
        return flow {
            val src = BitmapFactory.decodeResource(resources, R.mipmap.ic_test)
            emit(src)
        }.flowOn(Dispatchers.IO)
    }

    @Composable
    private fun Content() {
        MediaFactoryTheme(title = intent.getStringExtra("title") ?: "") {
            var title: String by remember {
                mutableStateOf(getString(R.string.film))
            }
            Column {
                val image: Flow<Bitmap> by remember {
                    mutableStateOf(loadImage())
                }
                val source: Bitmap by image.collectAsState(
                    initial = BitmapFactory.decodeResource(
                        resources,
                        R.mipmap.loading
                    )
                )
                Image(
                    painter = BitmapPainter(
                        source.asImageBitmap()
                    ), contentDescription = ""
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    if (MediaHandler.negative(source) == 1) {
                        title = if (title == getString(R.string.original)) {
                            getString(R.string.film)
                        } else {
                            getString(R.string.original)
                        }
                    }
                }) {
                    Text(text = title)
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    MediaHandler.flip(source, MediaHandler.FlipDirection.VERTICAL)

                }) {
                    Text(text = getString(R.string.flip_vertical))
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    MediaHandler.sendVideoCommand(MediaHandler.SplitVideoCommand(""))
                    MediaHandler.flip(source, MediaHandler.FlipDirection.HORIZONTAL)
                }) {
                    Text(text = getString(R.string.flip_horizontal))
                }
            }

        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun DisplayView() {
        Content()
    }
}
