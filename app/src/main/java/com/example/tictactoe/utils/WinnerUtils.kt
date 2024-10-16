package com.example.tictactoe.utils

import androidx.compose.runtime.snapshots.SnapshotStateMap

fun checkIfGameEnded(map: SnapshotStateMap<Int, Char>): Boolean {
    val gameWinners = listOf(
        listOf(0, 1, 2),
        listOf(3, 4, 5),
        listOf(6, 7, 8),
        listOf(0, 4, 8),
        listOf(2, 4, 6),
        listOf(0, 3, 6),
        listOf(1, 4, 7),
        listOf(2, 5, 8)
    )

    var index1: Int
    var index2: Int
    var index3: Int
    var value1: Char
    var value2: Char
    var value3: Char

    for (indexes in gameWinners) {
        index1 = indexes[0]; index2 = indexes[1]; index3 = indexes[2]
        value1 = map[index1]!!; value2 = map[index2]!!; value3 = map[index3]!!

        if (value1 != Char.MIN_VALUE && value2 != Char.MIN_VALUE && value3 != Char.MIN_VALUE && value1 == value2 && value2 == value3) {
            return true
        }
    }

    return false
}

fun winnerIndexes(
    player: Boolean, mappedGame: SnapshotStateMap<Char, List<Int>>
): MutableList<Int> {
    return if (player) {
        mappedGame['x']!!.toMutableList()
    } else {
        mappedGame['o']!!.toMutableList()
    }
}