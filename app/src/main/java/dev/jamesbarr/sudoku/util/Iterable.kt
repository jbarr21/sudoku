package dev.jamesbarr.sudoku.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

suspend fun <A, B> Iterable<A>.pmap(f: (A) -> B): List<B> = coroutineScope {
  map { async(Dispatchers.IO) { f(it) } }.awaitAll()
}
