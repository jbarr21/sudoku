package dev.jamesbarr.sudoku.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.ui.tooling.preview.Preview
import dev.jamesbarr.sudoku.viewmodel.GameViewModel
import kotlin.random.Random

@Composable
fun GameList(
  gameViewModel: GameViewModel,
  navController: NavController
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Sudoku") },
        contentColor = MaterialTheme.colors.onSurface,
        backgroundColor = MaterialTheme.colors.primaryVariant,
        actions = {
          IconButton(onClick = { navController.navigate("settings") }) {
            Icon(asset = Icons.Default.Settings)
          }
        }
      )
    },
    floatingActionButton = {
      FloatingActionButton(onClick = { launchGame(navController, Random(System.currentTimeMillis()).nextLong(1, 1000)) }) {
        Icon(asset = Icons.Default.PlayArrow)
      }
    },
    bodyContent = {
      Box(modifier = Modifier.fillMaxSize()) {
        Button(
          modifier = Modifier.align(Alignment.Center),
          onClick = { launchGame(navController, 0) }
        ) {
          Text("Play Game")
        }
      }
    }
  )
}

private fun launchGame(navController: NavController, gameId: Long) {
  navController.navigate("${Screen.Game.route}/$gameId")
}

@Preview
@Composable
fun GameListPreview() {
  SudokuTheme(darkTheme = true) {
    GameList(
      gameViewModel = GameViewModel.Factory(ContextAmbient.current).create(GameViewModel::class.java),
      navController = NavController(ContextAmbient.current)
    )
  }
}
