package dev.jamesbarr.sudoku.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import dev.jamesbarr.sudoku.domain.CellPosition
import dev.jamesbarr.sudoku.domain.IntBoard
import dev.jamesbarr.sudoku.domain.SudokuBoard
import dev.jamesbarr.sudoku.domain.isValidCell
import dev.jamesbarr.sudoku.domain.num
import dev.jamesbarr.sudoku.domain.tips
import kotlin.math.pow

const val SIZE = 3

@Composable
fun Board(
  board: SudokuBoard = IntBoard(),
  selectedCell: CellPosition? = null,
  maskCells: Boolean = false,
  onCellClick: ((CellPosition) -> Unit)? = null
) {
  Box(
    modifier = Modifier
      .border(1.dp, MaterialTheme.colors.background)
      .aspectRatio(1f)
  ) {
    Grid(
      cellFactory = { blockRow, blockCol ->
        Box(
          modifier = Modifier.border(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
        ) {
          Grid(
            cellFactory = { row, col ->
              val cellPos = CellPosition(blockRow * SIZE + blockCol, row, col)
              val pos = cellPos.boardIndex
              Cell(
                pos = cellPos,
                num = board.num(pos),
                tips = board.tips(pos),
                selected = cellPos == selectedCell,
                selectedNum = selectedCell?.let { board.num(it.boardIndex) },
                startValue = board.isStartingValue(pos),
                valid = board.isValidCell(pos),
                masked = maskCells,
                onCellClick = onCellClick
              )
            }
          )
        }
      }
    )
  }
}

@Composable
private fun Cell(
  pos: CellPosition,
  num: Int? = null,
  tips: List<Int> = emptyList(),
  selected: Boolean = false,
  selectedNum: Int? = null,
  startValue: Boolean = false,
  valid: Boolean = true,
  masked: Boolean = false,
  onCellClick: ((CellPosition) -> Unit)? = null,
) {
  val cellBgColor = when {
    selected -> MaterialTheme.colors.primaryVariant.copy(alpha = 0.5f)
    num != null && num == selectedNum -> MaterialTheme.colors.primaryVariant.copy(alpha = 0.25f)
    else -> Color.Transparent
  }

  val cellTextColor = when {
    startValue -> MaterialTheme.colors.onSurface
    else -> MaterialTheme.colors.primaryVariant
  }

  val textStyle = if (masked && NUM_COLUMNS > 1) {
    MaterialTheme.typography.body1
  } else {
    MaterialTheme.typography.h5
  }

  Box(
    Modifier.aspectRatio(1f)
      .background(if (valid) Color.Transparent else MaterialTheme.colors.error.copy(alpha = 0.50f))
  ) {
    Box(
      modifier = Modifier.aspectRatio(1f)
        .border(0.5.dp, MaterialTheme.colors.onSurface.copy(0.25f))
        .ifTrue(onCellClick != null, Modifier.clickable(onClick = { onCellClick?.invoke(pos) }))
        .background(color = cellBgColor)
    ) {
      if (num != null && num > 0) {
        Text(
          text = if (masked) "•" else num.toString(),
          color = cellTextColor,
          style = textStyle,
          modifier = Modifier.align(Alignment.Center)
        )
      } else if (!masked && tips.isNotEmpty()) {
        Tips(tips = tips)
      } else {
        Text(
          text = "",
          style = MaterialTheme.typography.h5,
          modifier = Modifier.align(Alignment.Center)
        )
      }
    }
  }
}

@Composable
private fun Tips(tips: List<Int> = emptyList(), size: Int = 3) {
  Grid(
    cellFactory = { row, col ->
      val num = row * size + col + 1
      val hasTip = num in tips
      Box(
        modifier = Modifier.aspectRatio(1f)
          .ifTrue(hasTip, Modifier.background(MaterialTheme.colors.background))
      ) {
        if (num in tips) {
          Text(
            text = num.toString(),
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.align(Alignment.Center)
          )
        }
      }
    }
  )
}

@Composable
private fun Grid(
  cellFactory: @Composable (row: Int, col: Int) -> Unit,
  size: Int = SIZE
) {
  Column(modifier = Modifier.aspectRatio(1f)) {
    (0 until size).forEach { row ->
      Row(modifier = Modifier.fillMaxWidth().weight(1f / size.toFloat())) {
        repeat(size) { col ->
          cellFactory(row, col)
        }
      }
    }
  }
}

fun Modifier.ifTrue(boolean: Boolean, modifier: Modifier): Modifier {
  return this.let {
    if (boolean) it.then(modifier) else it
  }
}

infix fun Int.pow(exponent: Int): Int = toDouble().pow(exponent).toInt()

@Preview(heightDp = 150)
@Composable
fun CellPreviews() {
  val pos = CellPosition(0, 0, 0)
  SudokuTheme(darkTheme = true) {
    Row {
      Cell(pos = pos, 3)
      Cell(pos = pos, 3, selected = true)
      Cell(pos = pos, 3, selected = false, selectedNum = 3)
    }
  }
}

@Preview(heightDp = 150)
@Composable
fun StartValuesPreviews() {
  val pos = CellPosition(0, 0, 0)
  SudokuTheme(darkTheme = true) {
    Row {
      Cell(pos = pos, 3, startValue = true)
      Cell(pos = pos, 3, startValue = true, selected = true)
      Cell(pos = pos, 3, startValue = true, selected = false, selectedNum = 3)
    }
  }
}

@Preview(heightDp = 150)
@Composable
fun ErrorValuesPreviews() {
  val pos = CellPosition(0, 0, 0)
  SudokuTheme(darkTheme = true) {
    Row {
      Cell(pos = pos, 3, startValue = true, valid = false)
      Cell(pos = pos, 3, startValue = true, selected = true, valid = false)
      Cell(pos = pos, 3, startValue = true, selected = false, selectedNum = 3, valid = false)
    }
  }
}

@Preview(heightDp = 150)
@Composable
fun TipsPreviews() {
  val pos = CellPosition(0, 0, 0)
  SudokuTheme(darkTheme = true) {
    Row {
      Cell(pos = pos, null, listOf(6, 9))
      Cell(pos = pos, tips = listOf(6, 9), selected = true)
    }
  }
}

@Preview(heightDp = 150)
@Composable
fun MaskedPreviews() {
  val pos = CellPosition(0, 0, 0)
  SudokuTheme(darkTheme = true) {
    Row {
      Cell(pos = pos, 0, masked = true)
      Cell(pos = pos, null, masked = true)
      Cell(pos = pos, null, selected = true, masked = true)
    }
  }
}

@Preview(heightDp = 150)
@Composable
fun EmptyPreviews() {
  val pos = CellPosition(0, 0, 0)
  SudokuTheme(darkTheme = true) {
    Row {
      Cell(pos = pos, 0)
      Cell(pos = pos, null)
      Cell(pos = pos, null, selected = true)
    }
  }
}
