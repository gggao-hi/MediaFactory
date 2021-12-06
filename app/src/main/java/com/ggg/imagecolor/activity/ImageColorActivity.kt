package com.ggg.imagecolor.activity

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ggg.handler.NativeLib
import com.ggg.mediafactory.R
import com.ggg.mediafactory.ui.theme.MediaFactoryTheme

class ImageColorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    @Composable
    private fun Content() {

        val src = BitmapFactory.decodeResource(resources, R.mipmap.ic_test)
        MediaFactoryTheme(title = intent.getStringExtra("title") ?: "") {
            var title: String by remember {
                mutableStateOf(getString(R.string.film))
            }
            Column(modifier = Modifier.verticalScroll(ScrollState(0))) {
                val source by remember {
                    mutableStateOf(src)
                }
                Image(
                    painter = BitmapPainter(
                        source.asImageBitmap()
                    ), contentDescription = ""
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    if (NativeLib.negative(src) == 1) {
                        title = if (title == getString(R.string.original)) {
                            getString(R.string.film)
                        } else {
                            getString(R.string.original)
                        }
                    }
                }) {
                    Text(text = title)
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
