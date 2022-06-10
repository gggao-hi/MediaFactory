package com.ggg.share_remote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.Flow

class ShareRemoteActivity : ComponentActivity() {
    private var remoteViewModel: RemoteViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        remoteViewModel = ViewModelProvider(this).get(RemoteViewModel::class.java)
        setContent {
            loadView()
        }
    }

    @Preview
    @Composable
    private fun loadView() {
        remoteViewModel?.apply {
            val data: Flow<RemoteBean?> by remember {
                mutableStateOf(this.fetch())
            }
            data.collectAsState(initial = null).apply {
                this.value?.takeIf {
                    it.isSuccess
                }?.also { bean ->
                    Column {
                        Row(Modifier.horizontalScroll(ScrollState(0))) {
                            bean.folders?.forEach { folder ->
                                Button(onClick = { }) {
                                    Row {
                                        Text(text = "${folder.name}")
                                    }

                                }
                            }
                        }
                        Column(Modifier.verticalScroll(ScrollState(0))) {
                            bean.fileItems?.forEach { item ->

                                Button(onClick = { /*TODO*/ }) {
                                    Column {
                                        Icon(
                                            painter = painterResource(id = item.type.icon),
                                            contentDescription = null
                                        )
                                        Text(text = "${item.name}")
                                    }

                                }
                            }
                        }
                    }
                }

            }
        }


    }
}