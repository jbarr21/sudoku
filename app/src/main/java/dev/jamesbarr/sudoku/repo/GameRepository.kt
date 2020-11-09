package dev.jamesbarr.sudoku.repo

import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types.newParameterizedType
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.jamesbarr.sudoku.domain.CellPosition
import dev.jamesbarr.sudoku.domain.IntBoard
import dev.jamesbarr.sudoku.domain.SudokuGame
import dev.jamesbarr.sudoku.domain.SudokuSolver
import dev.jamesbarr.sudoku.util.pmap
import java.io.IOException
import kotlin.random.Random
import kotlin.random.Random.Default.nextLong

class GameRepository(context: Context) {
  var games: List<SudokuGame> = emptyList()
  var game: SudokuGame = generateGame(0)
  var selectedCell: CellPosition? = null

  private val moshi by lazy { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }
  private val prefs by lazy { PreferenceManager.getDefaultSharedPreferences(context.applicationContext) }

  fun startGame(gameId: Long): SudokuGame {
    game = games[(gameId % NUM_GAMES).toInt()]
    game.calculateSolution()
    return game
  }

  suspend fun loadGames(): List<SudokuGame> {
    games = loadGamesFromDisk() ?: generateGames()
    return games
  }

  private fun loadGamesFromDisk(): List<SudokuGame>? {
    val type = newParameterizedType(List::class.java, SudokuGame::class.java)
    val adapter = moshi.adapter<List<SudokuGame>>(type)
    return prefs.getString(PREF_GAMES, null)?.let { gamesJson ->
      try {
        val games = adapter.fromJson(gamesJson)
        games
      } catch (e: IOException) {
        null
      }
    }.also {
      it?.let { games = it }
    }
  }

  private suspend fun generateGames(): List<SudokuGame> {
    return (0 until NUM_GAMES)
      .pmap { generateGame(it.toLong()) }
      .also { saveGeneratedGames(it) }
  }

  private fun generateGame(seed: Long = nextLong()): SudokuGame {
    val pair = SudokuSolver.generate(random = Random(seed))
    return SudokuGame(pair.first as IntBoard, seed).apply {
      solvedBoard = pair.second as IntBoard
    }
  }

  @SuppressLint("ApplySharedPref")
  fun saveGeneratedGames(games: List<SudokuGame>) {
    if (games.isEmpty()) return
    val type = newParameterizedType(List::class.java, SudokuGame::class.java)
    val adapter = moshi.adapter<List<SudokuGame>>(type)
    val gamesJson = adapter.toJson(games)
    val success = prefs.edit().putString(PREF_GAMES, gamesJson).commit()
  }

  companion object {
    const val NUM_GAMES = 100
    private const val PREF_GAMES = "games"
  }
}
