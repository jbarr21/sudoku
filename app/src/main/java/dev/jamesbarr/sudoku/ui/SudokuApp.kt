package dev.jamesbarr.sudoku.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType.IntType
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
      GameList(navController)
    }
    composable(
      "${Screen.Game.route}/{gameId}",
      arguments = listOf(navArgument("gameId") { type = IntType })
    ) { backStackEntry ->
      val gameId = backStackEntry.arguments?.getInt("gameId") ?: throw IllegalStateException("missing game id")
      GameUi(
        gameViewModel = gameViewModel,
        navController = navController,
        gameId = gameId
      )
    }
  }
}
