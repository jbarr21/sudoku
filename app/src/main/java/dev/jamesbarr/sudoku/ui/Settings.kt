package dev.jamesbarr.sudoku.ui

import androidx.compose.material.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Settings(navController: NavController) {
  Scaffold(
    topBar = { SudokuTopAppBar(title = "Settings", navController = navController) },
    bodyContent = {
      Box(modifier = Modifier.fillMaxSize()) {
        Text("Settings", modifier = Modifier.align(Alignment.Center))
      }
    }
  )
}

@Preview
@Composable
fun SettingsPreview() {
  SudokuTheme(darkTheme = true) {
    Settings(
      navController = NavController(AmbientContext.current)
    )
  }
}
