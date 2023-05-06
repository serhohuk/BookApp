package com.books.app.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

data class AppColors(
    val material: Colors,
    val pink: Color,
    val darkPink: Color,
    val lightGray: Color
)

private val customColors = AppColors(
    material = lightColors(),
    pink = Color(0xFFDD48A1),
    darkPink = Color(0xFFD0006E),
    lightGray = Color(0xFFC4C4C4)
)

val MaterialTheme.appColors: AppColors
    get() = customColors


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {

    MaterialTheme(colors = customColors.material) {
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null,
            content = content
        )
    }
}