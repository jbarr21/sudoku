package dev.jamesbarr.sudoku.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.jamesbarr.sudoku.domain.CellPosition
import dev.jamesbarr.sudoku.domain.IntBoard
import dev.jamesbarr.sudoku.domain.SudokuBoard
import dev.jamesbarr.sudoku.domain.num
import dev.jamesbarr.sudoku.repo.GameRepository
import dev.jamesbarr.sudoku.repo.GameRepository.Companion.NUM_GAMES
import dev.jamesbarr.sudoku.util.pmap
import kotlinx.coroutines.*
import java.time.Duration
import java.time.Instant

class GameViewModel(private val gameRepository: GameRepository) : ViewModel() {

  var games by mutableStateOf(gameRepository.games)
    private set
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
    " " to this::solve
  )

  init {
    loadGames()
  }

  fun startNewGame(gameId: Long) {
    isGameOver = false
    gameRepository.game = gameRepository.startGame(gameId)
    board = gameRepository.game.board
  }

  fun restartGame() {
    isGameOver = false
    board = gameRepository.game.board.copy().apply {
      toArray()
        .indices
        .filterNot { isStartingValue(it) }
        .forEach { clearValues(it) }
    }
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

  private fun loadGames() {
    val start = Instant.now()
    viewModelScope.launch(Dispatchers.IO) {
      gameRepository.games = (0 until NUM_GAMES)
        .pmap { gameRepository.generateGame(it.toLong()) }
        .apply {
          gameRepository.games = this
          games = this
          println("generated in: " + (Duration.between(start, Instant.now()).toMillis() / 1000f))
        }
    }
  }

  class Factory(context: Context) : ViewModelProvider.Factory {
    private val context = context.applicationContext

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
      @Suppress("UNCHECKED_CAST")
      return GameViewModel(GameRepository(context)) as T
    }
  }
}
