package com.ggg.base.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.runtime.Composable
import com.ggg.base.BaseApplication

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200
)

@Composable
fun MediaFactoryTheme(
    title: String = "Media Factory",
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes
    ) {
        Scaffold(topBar = {
            TopAppBar {
                Button(onClick = {
                    BaseApplication.getCurrentActivity()?.onBackPressed()
                }) {
                    Icon(imageVector = Icons.Sharp.ArrowBack, contentDescription = "")
                }
                Text(text = title)
            }
        }) {
            content()
        }
    }
}
