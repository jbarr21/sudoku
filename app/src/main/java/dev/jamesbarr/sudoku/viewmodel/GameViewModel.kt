package dev.jamesbarr.sudoku.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.jamesbarr.sudoku.domain.CellPosition
import dev.jamesbarr.sudoku.domain.IntBoard
import dev.jamesbarr.sudoku.domain.SudokuBoard
import dev.jamesbarr.sudoku.domain.num
import dev.jamesbarr.sudoku.repo.GameRepository

class GameViewModel(private val gameRepository: GameRepository) : ViewModel() {

  var board by mutableStateOf<SudokuBoard>(gameRepository.game.board)
    private set
  var selectedCell by mutableStateOf(gameRepository.selectedCell)
    private set
  var isEditMode by mutableStateOf(false)
    private set
  var isGameOver by mutableStateOf(false)
    private set

  val actions = listOf(
    "E" to this::toggleEditMode,
    "X" to this::clearCell,
    "S" to this::solve
  )

  fun startNewGame() {
    isGameOver = false
    gameRepository.game = gameRepository.generateGame()
    board = gameRepository.game.board
  }

  fun onCellClick(pos: CellPosition) {
    selectedCell = pos
    saveState(board, selectedCell)
  }

  fun onNumberClick(num: Int) {
    selectedCell?.let {
      if (!board.isStartingValue(it.boardIndex)) {
        board = board.copy().apply {
          val pos = it.boardIndex
          if (isEditMode) {
            if (num in possibleValues(pos)) {
              removePossibleValue(pos, num)
            } else {
              addPossibleValue(pos, num)
            }
          } else {
            setValue(pos, num, true)
          }
        }
        saveState(board, selectedCell)

        if (isBoardSolved()) {
          isGameOver = true
        }
      }
    }
  }

  fun clearCell() {
    selectedCell?.let {
      if (!board.isStartingValue(it.boardIndex)) {
        board = board.copy().apply {
          clearValues(it.boardIndex)
        }
      }
    }
  }

  fun solve() {
    board = gameRepository.game.solvedBoard
  }

  fun toggleEditMode() {
    this.isEditMode = !isEditMode
  }

  fun dismissDialog() {
    isGameOver = false
  }

  fun isBoardSolved(): Boolean {
    return board.toArray()
      .indices
      .all { board.num(it) == gameRepository.game.solvedBoard.num(it) }
  }

  fun saveState(board: SudokuBoard, selectedCell: CellPosition?) {
    gameRepository.game.board = board as IntBoard
    gameRepository.selectedCell = selectedCell
  }

  class Factory(context: Context) : ViewModelProvider.Factory {
    private val context = context.applicationContext

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
      @Suppress("UNCHECKED_CAST")
      return GameViewModel(GameRepository(context)) as T
    }
  }
}
