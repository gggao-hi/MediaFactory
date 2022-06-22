package com.ggg.share_remote.declarativeui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.ggg.base.ui.theme.MediaFactoryTheme
import com.ggg.share_remote.RemoteViewModel

class ComposeShareRemoteActivity : ComponentActivity() {
    private var remoteViewModel: RemoteViewModel? = null

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        remoteViewModel = ViewModelProvider(this).get(RemoteViewModel::class.java)
        setContent {
            LoadView()
        }
        remoteViewModel?.fetch("")
    }

    @ExperimentalFoundationApi
    @Preview
    @Composable
    private fun LoadView() {
        MediaFactoryTheme(title = intent.getStringExtra("title") ?: "") {
            remoteViewModel?.apply {
                remoteBean.apply {
                    this.takeIf {
                        it.isSuccess
                    }?.also { bean ->
                        Column {
                            LazyRow {
                                items(bean.folders!!.size) { index ->
                                    val item =
                                        bean.folders[if (index >= bean.folders.size) 0 else index]
                                    TextButton(onClick = { fetch(item.id) }) {
                                        Text(text = "${item.name} >")
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.size(20.dp))
                            LazyVerticalGrid(cells = GridCells.Adaptive(80.dp)) {
                                items(bean.fileItems!!.size) { index ->
                                    val item = bean.fileItems[index]
                                    IconButton(onClick = { fetch(item.id) }) {
                                        Column {
                                            Icon(
                                                painter = painterResource(id = item.type.icon),
                                                contentDescription = item.name
                                            )
                                            Text(text = item.name)
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
}