package dev.jamesbarr.sudoku.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.jamesbarr.sudoku.domain.SudokuGame
import dev.jamesbarr.sudoku.repo.GameRepository

@Composable
fun GameCard(
  game: SudokuGame,
  onClick: () -> Unit = {}
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp)
      .clickable(onClick = onClick)
  ) {
    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
      Text(
        text = "Game #${game.id + 1}",
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(bottom = 8.dp)
      )
      Board(board = game.board, maskCells = true)
    }
  }
}

@Preview
@Composable
fun GameCardPreview() {
  val game = GameRepository(AmbientContext.current).game
  Row(modifier = Modifier.fillMaxWidth()) {
    repeat(NUM_COLUMNS) {
      Box(modifier = Modifier.weight(0.5f)) {
        GameCard(game = game)
      }
    }
  }
}
