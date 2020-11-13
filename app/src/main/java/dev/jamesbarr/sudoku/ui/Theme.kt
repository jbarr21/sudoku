package dev.jamesbarr.sudoku.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(primaryVariant = Color(0xff3f51b5))
private val LightColorPalette = lightColors(primaryVariant = Color(0xff1da9da))

@Composable
fun SudokuTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
  val colors = if (darkTheme)
    DarkColorPalette
  else
    LightColorPalette
  MaterialTheme(
    colors = colors,
    content = content
  )
}
