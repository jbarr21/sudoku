package dev.jamesbarr.sudoku.ui

import androidx.annotation.StringRes
import dev.jamesbarr.sudoku.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
  object List : Screen("list", R.string.list)
  object Game : Screen("game", R.string.game)
  object Settings : Screen("settings", R.string.game)

  companion object {
    val items = listOf(
      List,
      Game
    )
  }
}
