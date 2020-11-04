package dev.jamesbarr.sudoku.domain

import dev.jamesbarr.sudoku.ui.SIZE
import dev.jamesbarr.sudoku.ui.pow

data class CellPosition(
  val block: Int,
  val row: Int,
  val col: Int
) {
  val boardIndex = boardIndexForPos(block, row, col)

  companion object {
    fun boardIndexForPos(block: Int, row: Int, col: Int): Int {
      return (block / SIZE) * SIZE.pow(3) + // account for start of block
        row * SIZE.pow(2) + // account for row within block
        (block % SIZE) * SIZE + // account for column from block
        col // account for column within block
    }
  }
}
