package dev.jamesbarr.sudoku.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import dev.jamesbarr.sudoku.viewmodel.GameViewModel

@Composable
fun GameUi(
  gameViewModel: GameViewModel,
  navController: NavController,
  gameId: Long = 0
) {
  Scaffold(
    topBar = {
      SudokuTopAppBar(
        title = "Game #${gameId + 1}",
        navController = navController,
        actions = {
          IconButton(onClick = gameViewModel::restartGame) {
            Icon(Icons.Default.Refresh, contentDescription = null)
          }
        }
      )
    },
    bodyContent = {
      Box {
        Column {
          Board(
            board = gameViewModel.board,
            selectedCell = gameViewModel.selectedCell,
            onCellClick = gameViewModel::onCellClick
          )
          EntryPad(
            editMode = gameViewModel.isEditMode,
            onNumberClick = gameViewModel::onNumberClick,
            actions = gameViewModel.actions
          )
        }
        if (gameViewModel.isGameOver) {
          GameDialog(
            title = "Congrats",
            message = "You solved the puzzle!",
            primaryLabel = "New Game",
            secondaryLabel = "Done",
            onConfirmListener = { navController.popBackStack() },
            onDismissListener = gameViewModel::dismissDialog
          )
        }
      }
    }
  )
}

@Preview
@Composable
fun GameUiPreview() {
  SudokuTheme(darkTheme = true) {
    GameUi(
      gameViewModel = GameViewModel.Factory(AmbientContext.current).create(GameViewModel::class.java),
      navController = NavController(AmbientContext.current)
    )
  }
}
