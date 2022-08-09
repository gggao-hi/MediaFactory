package com.ggg.enter.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.app.ActivityCompat
import com.ggg.base.ui.theme.MediaFactoryTheme
import com.ggg.enter.model.EnterRepository

class MainActivity : ComponentActivity() {
    private val enterRepository = EnterRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
        requestPermission()
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

    private fun requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
            PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
            PackageManager.PERMISSION_GRANTED||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
        ) {
            val launcher: ActivityResultLauncher<Array<String>> =
                registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                }

            launcher.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                )
            )
        }
    }

}
