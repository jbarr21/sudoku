package dev.jamesbarr.sudoku.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.ui.tooling.preview.Preview
import dev.jamesbarr.sudoku.R
import dev.jamesbarr.sudoku.viewmodel.GameViewModel
import kotlin.random.Random.Default.nextLong

const val NUM_COLUMNS = 2

@Composable
fun GameList(
  gameViewModel: GameViewModel,
  navController: NavController
) {
  Scaffold(
    topBar = {
      SudokuTopAppBar(
        title = "Sudoku",
        navController = navController,
        actions = {
          IconButton(onClick = { navController.navigate(Screen.Settings.route) }) {
            Icon(asset = Icons.Default.Settings)
          }
        }
      )
    },
    floatingActionButton = {
      FloatingActionButton(
        backgroundColor = MaterialTheme.colors.primary,
        onClick = { navController.launchGame(nextLong(1, 1000)) }
      ) {
        Icon(
          asset = vectorResource(id = R.drawable.ic_casino),
          tint = MaterialTheme.colors.onSurface
        )
      }
    },
    bodyContent = {
      val numGames = gameViewModel.games.size
      if (numGames == 0) {
        GameListLoading()
      } else {
        GameListData(gameViewModel, navController)
      }
    }
  )
}

@Composable
fun GameListData(
  gameViewModel: GameViewModel,
  navController: NavController
) {
  LazyColumnFor(items = (0 until (gameViewModel.games.size / NUM_COLUMNS)).toList()) { row ->
    Row(modifier = Modifier.fillMaxWidth()) {
      repeat(NUM_COLUMNS) { col ->
        val gameId = (row * NUM_COLUMNS + col).toLong()
        Box(modifier = Modifier.weight(0.5f)) {
          GameCard(
            game = gameViewModel.games[row * NUM_COLUMNS + col],
            onClick = { navController.launchGame(gameId) }
          )
        }
      }
    }
  }
}

@Composable
fun GameListLoading() {
  Box(modifier = Modifier.fillMaxSize()) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.align(Alignment.Center)
    ) {
      CircularProgressIndicator()
      Text(
        text = "Generating games...",
        modifier = Modifier.padding(top = 8.dp)
      )
    }
  }
}

private fun NavController.launchGame(gameId: Long) = navigate(Screen.Game.routeFor(gameId))

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
