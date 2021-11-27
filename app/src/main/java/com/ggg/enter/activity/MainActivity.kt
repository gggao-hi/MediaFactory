package com.ggg.enter.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ggg.enter.model.EnterRepository
import com.ggg.mediafactory.ui.theme.MediaFactoryTheme

class MainActivity : ComponentActivity() {
    private val enterRepository = EnterRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    @Composable
    private fun Content() {
        MediaFactoryTheme(title = "Enters") {
            Column(
                modifier = Modifier
                    .verticalScroll(ScrollState(1))
            ) {
                enterRepository.obtainEnters().forEach {
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 1.dp),
                        onClick = { it.handler() },
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = Color.Cyan,
                            contentColor = Color.Red
                        ),
                    ) {
                        Text(text = it.name)
                    }
                }
            }

        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun DefaultPreview() {
        Content()
    }
}
