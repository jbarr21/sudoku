package dev.jamesbarr.sudoku.domain

import kotlin.math.sqrt

val SudokuBoard.size: Int
  get() = sqrt(this.toArray().size.toDouble()).toInt()

fun SudokuBoard.num(pos: Int) = if (hasValue(pos)) getFirstPossibleValue(pos) else null

fun SudokuBoard.tips(pos: Int) = if (hasValue(pos)) emptyList() else possibleValues(pos)

fun SudokuBoard.isValidCell(pos: Int) = isValidRow(pos) && isValidCol(pos) && isValidBlock(pos)

private fun SudokuBoard.isValidRow(pos: Int): Boolean {
  val row = pos / size
  return (0 until size)
    .map { col -> row * size + col }
    .filter { pos -> hasValue(pos) }
    .map { pos -> num(pos) }
    .let { it.size == it.toSet().size }
}

private fun SudokuBoard.isValidCol(pos: Int): Boolean {
  val col = pos % size
  return (0 until size)
    .map { row -> row * size + col }
    .filter { pos -> hasValue(pos) }
    .map { pos -> num(pos) }
    .let { it.size == it.toSet().size }
}

private fun SudokuBoard.isValidBlock(pos: Int): Boolean {
  val blockSize = sqrt(size.toDouble()).toInt()
  val block = blockForBoardIndex(pos)
  val blockStartRow = (block / blockSize) * blockSize
  val blockStartCol = (block % blockSize) * blockSize

  return (0 until blockSize)
    .flatMap { blockRow -> (0 until blockSize).map { blockCol -> blockRow to blockCol } }
    .map { (blockRow, blockCol) -> (blockStartRow + blockRow) * size + (blockStartCol + blockCol) }
    .filter { pos -> hasValue(pos) }
    .map { pos -> num(pos) }
    .let { it.size == it.toSet().size }
}

private fun SudokuBoard.hasValue(pos: Int) = isCommitedValue(pos) || isStartingValue(pos)

private fun SudokuBoard.blockForBoardIndex(pos: Int): Int {
  val blockSize = sqrt(size.toDouble()).toInt()
  val row = pos / size
  val col = pos % size
  return (row / blockSize) * blockSize + (col / blockSize)
}
