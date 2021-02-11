package dev.jamesbarr.sudoku.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun SudokuTopAppBar(
  title: String,
  navController: NavController,
  actions: @Composable RowScope.() -> Unit = {}
) {
  TopAppBar(
    title = { Text(text = title) },
    contentColor = MaterialTheme.colors.onSurface,
    backgroundColor = MaterialTheme.colors.primaryVariant,
    actions = actions,
    navigationIcon = navController.previousBackStackEntry?.let {
      {
        IconButton(onClick = { navController.popBackStack() }) {
          Icon(Icons.Default.ArrowBack, contentDescription = null)
        }
      }
    }
  )
}
