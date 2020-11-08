package dev.jamesbarr.sudoku

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.setContent
import dev.jamesbarr.sudoku.ui.SudokuApp
import dev.jamesbarr.sudoku.ui.SudokuTheme
import dev.jamesbarr.sudoku.viewmodel.GameViewModel

// TODO: add animations for text change & bg color change
// TODO: list of games
// TODO: load game from datastore/prefs
class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val gameViewModel by viewModels<GameViewModel> { GameViewModel.Factory(this) }

    setContent {
      SudokuTheme {
        Surface(color = MaterialTheme.colors.background) {
          SudokuApp(gameViewModel)
        }
      }
    }
  }
}
