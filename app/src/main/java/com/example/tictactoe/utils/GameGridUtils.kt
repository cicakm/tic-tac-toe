package com.example.tictactoe.utils

import androidx.compose.runtime.snapshots.SnapshotStateMap

fun initMap(gameGrid: SnapshotStateMap<Int, Char>) {
    gameGrid[0] = Char.MIN_VALUE
    gameGrid[1] = Char.MIN_VALUE
    gameGrid[2] = Char.MIN_VALUE
    gameGrid[3] = Char.MIN_VALUE
    gameGrid[4] = Char.MIN_VALUE
    gameGrid[5] = Char.MIN_VALUE
    gameGrid[6] = Char.MIN_VALUE
    gameGrid[7] = Char.MIN_VALUE
    gameGrid[8] = Char.MIN_VALUE
}

fun fillMaps(
    gameGrid: SnapshotStateMap<Int, Char>,
    mappedGame: SnapshotStateMap<Char, List<Int>>,
    index: Int,
    player: Boolean
) {
    val text = if (player) 'x' else 'o'
    if (mappedGame['o']!!.size < 3 || mappedGame['x']!!.size < 3) {
        mappedGame[text] = mappedGame[text]!!.plus(index)
        gameGrid[index] = text
    } else {
        gameGrid[mappedGame[text]!!.first()] = Char.MIN_VALUE
        gameGrid[index] = text
        mappedGame[text] = mappedGame[text]!!.drop(1).plus(index)
    }
}

fun nextInChange(mappedGame: SnapshotStateMap<Char, List<Int>>, player: Boolean): Int {
    if (mappedGame['x']!!.size == 3 && mappedGame['o']!!.size == 3) {
        return if (player) {
            mappedGame['o']!!.first()
        } else {
            mappedGame['x']!!.first()
        }
    }
    return -1
}