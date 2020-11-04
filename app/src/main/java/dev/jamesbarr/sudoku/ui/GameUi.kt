package dev.jamesbarr.sudoku.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun GameUi(
  board: @Composable () -> Unit,
  entryPad: @Composable () -> Unit,
  gameOverDialog: @Composable () -> Unit,
  isGameOver: Boolean = false
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Sudoku") },
        contentColor = MaterialTheme.colors.onSurface,
        backgroundColor = MaterialTheme.colors.primaryVariant
      )
    },
    bodyContent = {
      Box {
        Column {
          board()
          entryPad()
        }
        if (isGameOver) {
          gameOverDialog()
        }
      }
    }
  )
}
