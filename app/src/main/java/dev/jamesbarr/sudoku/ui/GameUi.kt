package dev.jamesbarr.sudoku.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ContextAmbient
import androidx.navigation.NavController
import androidx.ui.tooling.preview.Preview
import dev.jamesbarr.sudoku.domain.GameDialog
import dev.jamesbarr.sudoku.viewmodel.GameViewModel

@Composable
fun GameUi(
  gameViewModel: GameViewModel,
  navController: NavController,
  gameId: Int = 0
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Sudoku") },
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
            onConfirmListener = gameViewModel::startNewGame,
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
      gameViewModel = GameViewModel.Factory(ContextAmbient.current).create(GameViewModel::class.java),
      navController = NavController(ContextAmbient.current)
    )
  }
}
