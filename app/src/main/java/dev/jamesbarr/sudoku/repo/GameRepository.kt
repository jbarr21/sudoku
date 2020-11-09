package dev.jamesbarr.sudoku.repo

import android.content.Context
import dev.jamesbarr.sudoku.domain.CellPosition
import dev.jamesbarr.sudoku.domain.IntBoard
import dev.jamesbarr.sudoku.domain.SudokuGame
import dev.jamesbarr.sudoku.domain.SudokuSolver
import kotlin.random.Random
import kotlin.random.Random.Default.nextLong

class GameRepository(context: Context) {
  var games: List<SudokuGame> = emptyList()
  var game: SudokuGame = generateGame(0)
  var selectedCell: CellPosition? = null

  fun generateGame(seed: Long = nextLong()): SudokuGame {
    println("Generating $seed")
    val pair = SudokuSolver.generate(random = Random(seed))
    println("Finished $seed")
    return SudokuGame(pair.first as IntBoard, seed).apply {
      solvedBoard = pair.second as IntBoard
    }
  }

  fun startGame(gameId: Long): SudokuGame {
    game = generateGame(seed = gameId)
    return game
  }

  companion object {
    const val NUM_GAMES = 20
  }
}
