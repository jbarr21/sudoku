package dev.jamesbarr.sudoku.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType.LongType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import dev.jamesbarr.sudoku.viewmodel.GameViewModel

@Composable
fun SudokuApp(gameViewModel: GameViewModel) {
  val navController = rememberNavController()
  NavHost(navController, startDestination = Screen.List.route) {
    composable(Screen.List.route) {
      GameList(
        gameViewModel = gameViewModel,
        navController = navController
      )
    }
    composable(
      "${Screen.Game.route}/{gameId}",
      arguments = listOf(navArgument("gameId") { type = LongType })
    ) { backStackEntry ->
      val gameId = backStackEntry.arguments?.getLong("gameId") ?: throw IllegalStateException("missing game id")
      gameViewModel.startNewGame(gameId)
      GameUi(
        gameViewModel = gameViewModel,
        navController = navController,
        gameId = gameId
      )
    }
    composable(Screen.Settings.route) {
      Settings(navController)
    }
  }
}
