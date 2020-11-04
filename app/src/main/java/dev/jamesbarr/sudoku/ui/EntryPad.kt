package dev.jamesbarr.sudoku.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.RippleIndication
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.ui.tooling.preview.Preview

@Preview
@Composable
fun EntryPad(
  actions: List<Pair<String, () -> Unit>> = listOf("E" to {}, "X" to {}),
  onNumberClick: (Int) -> Unit = {},
  editMode: Boolean = false,
) {
  Row(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.surface)) {
    (0 until SIZE + 1).forEach { col ->
      Column(modifier = Modifier.weight(1f / (SIZE + 1).toFloat())) {
        repeat(SIZE) { row ->
          val modifier = Modifier.weight(1f / (SIZE + 1).toFloat())
          val isAction = col == SIZE
          if (isAction) {
            val action = actions.getOrNull(row)
            EntryPadButton(
              label = action?.first ?: "",
              textColor = if (action?.first == "E" && editMode) {
                MaterialTheme.colors.primaryVariant
              } else {
                MaterialTheme.colors.onSurface
              },
              modifier = modifier,
              onClick = action?.second ?: {}
            )
          } else {
            val num = row * SIZE + col + 1
            EntryPadButton(
              label = num.toString(),
              modifier = modifier,
              onClick = { onNumberClick(num) }
            )
          }
        }
      }
    }
  }
}

@Composable
private fun EntryPadButton(
  label: String,
  textColor: Color = MaterialTheme.colors.onSurface,
  modifier: Modifier,
  onClick: () -> Unit = {}
) {
  Box(
    modifier = modifier.then(
      Modifier.fillMaxSize()
        .clickable(
          onClick = onClick,
          enabled = label.isNotEmpty(),
          indication = RippleIndication(bounded = false)
        )
    )
  ) {
    Text(
      text = label,
      color = textColor,
      style = MaterialTheme.typography.h5,
      modifier = Modifier.align(Alignment.Center)
    )
  }
}
