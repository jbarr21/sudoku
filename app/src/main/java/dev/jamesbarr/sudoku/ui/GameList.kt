package dev.jamesbarr.sudoku.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.navigate

@Composable
fun GameList(navController: NavController) {
  val gameId = 1
  Box(modifier = Modifier.fillMaxSize()) {
    Button(
      modifier = Modifier.align(Alignment.Center),
      onClick = { navController.navigate("${Screen.Game.route}/$gameId") }
    ) {
      Text("Play Game")
    }
  }
}

