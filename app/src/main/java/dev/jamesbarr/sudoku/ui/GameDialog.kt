package dev.jamesbarr.sudoku.ui

import androidx.compose.foundation.Text
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun GameDialog(
  title: String,
  message: String,
  primaryLabel: String = "OK",
  secondaryLabel: String = "Cancel",
  onConfirmListener: () -> Unit = {},
  onDismissListener: () -> Unit = {}
) {
  AlertDialog(
    title = { Text(text = title) },
    text = { Text(text = message) },
    confirmButton = { TextButton(onClick = { onConfirmListener.invoke() }) { Text(primaryLabel) } },
    dismissButton = { TextButton(onClick = { onDismissListener.invoke() }) { Text(secondaryLabel) } },
    onDismissRequest = { onDismissListener.invoke() },
  )
}
