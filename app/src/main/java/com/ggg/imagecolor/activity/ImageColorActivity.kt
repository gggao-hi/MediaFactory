package com.ggg.imagecolor.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ggg.mediafactory.ui.theme.MediaFactoryTheme

class ImageColorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DefaultPreview()
        }
    }


    @Preview(showBackground = true)
    @Composable
    private fun DefaultPreview() {
        MediaFactoryTheme(title = intent.getStringExtra("title") ?: "") {
            // A surface container using the 'background' color from the theme
            Surface(color = MaterialTheme.colors.background) {

            }
        }
    }
}
