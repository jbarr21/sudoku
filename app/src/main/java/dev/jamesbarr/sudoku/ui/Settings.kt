package dev.jamesbarr.sudoku.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.navigation.NavController
import androidx.ui.tooling.preview.Preview

@Composable
fun Settings(navController: NavController) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Settings") },
        contentColor = MaterialTheme.colors.onSurface,
        backgroundColor = MaterialTheme.colors.primaryVariant,
        navigationIcon = {
          IconButton(onClick = { navController.popBackStack() }) {
            Icon(asset = Icons.Default.ArrowBack)
          }
        }
      )
    },
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
      navController = NavController(ContextAmbient.current)
    )
  }
}
