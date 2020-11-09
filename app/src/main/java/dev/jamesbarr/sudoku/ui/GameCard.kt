package dev.jamesbarr.sudoku.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
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
      .clickable(onClick = onClick)
      .padding(8.dp)
  ) {
    Column(modifier = Modifier.padding(8.dp)) {
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
  GameCard(
    game = GameRepository(ContextAmbient.current).generateGame(0)
  )
}
