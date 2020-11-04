package dev.jamesbarr.sudoku

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.ui.tooling.preview.Preview
import dev.jamesbarr.sudoku.domain.GameDialog
import dev.jamesbarr.sudoku.ui.Board
import dev.jamesbarr.sudoku.ui.EntryPad
import dev.jamesbarr.sudoku.ui.GameUi
import dev.jamesbarr.sudoku.ui.SudokuTheme
import dev.jamesbarr.sudoku.viewmodel.GameViewModel

// TODO: add animations for text change & bg color change
// TODO: list of games
// TODO: load game from datastore/prefs
class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val gameViewModel by viewModels<GameViewModel> { GameViewModel.Factory(this) }

    setContent {
      SudokuTheme {
        Surface(color = MaterialTheme.colors.background) {
          GameUi(
            board = {
              Board(
                board = gameViewModel.board,
                selectedCell = gameViewModel.selectedCell,
                onCellClick = gameViewModel::onCellClick
              )
            },
            entryPad = {
              EntryPad(
                editMode = gameViewModel.isEditMode,
                onNumberClick = gameViewModel::onNumberClick,
                actions = gameViewModel.actions
              )
            },
            gameOverDialog = {
              GameDialog(
                title = "Congrats",
                message = "You solved the puzzle!",
                primaryLabel = "New Game",
                secondaryLabel = "Done",
                onConfirmListener = gameViewModel::startNewGame,
                onDismissListener = gameViewModel::dismissDialog
              )
            },
            isGameOver = gameViewModel.isGameOver
          )
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  SudokuTheme(darkTheme = true) {
    GameUi(board = { Board() }, entryPad = { EntryPad() }, gameOverDialog = {})
  }
}
