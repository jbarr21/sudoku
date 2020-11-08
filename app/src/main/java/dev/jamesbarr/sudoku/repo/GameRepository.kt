package dev.jamesbarr.sudoku.repo

import android.content.Context
import dev.jamesbarr.sudoku.domain.CellPosition
import dev.jamesbarr.sudoku.domain.IntBoard
import dev.jamesbarr.sudoku.domain.SudokuGame
import dev.jamesbarr.sudoku.domain.SudokuSolver
import kotlin.random.Random

class GameRepository(context: Context) {

  var game: SudokuGame = generateGame(0L)
  var selectedCell: CellPosition? = null

  fun generateGame(seed: Long = Random.nextLong()): SudokuGame {
    val pair = SudokuSolver.generate(random = Random(seed))
    return SudokuGame(pair.first as IntBoard, seed).apply {
      solvedBoard = pair.second as IntBoard
    }
  }

  fun startGame(gameId: Long): SudokuGame {
    game = generateGame(seed = gameId)
    return game
  }
}
