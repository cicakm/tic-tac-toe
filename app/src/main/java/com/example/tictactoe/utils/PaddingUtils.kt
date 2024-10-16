package com.example.tictactoe.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp

fun calculatePadding(index: Int): PaddingValues {
    when (index) {
        0 -> return PaddingValues(start = 0.dp, end = 4.dp, top = 0.dp, bottom = 4.dp)
        1 -> return PaddingValues(start = 2.dp, end = 2.dp, top = 0.dp, bottom = 0.dp)
        2 -> return PaddingValues(start = 4.dp, end = 0.dp, top = 0.dp, bottom = 4.dp)
        3 -> return PaddingValues(start = 0.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
        4 -> return PaddingValues(start = 2.dp, end = 2.dp, top = 4.dp, bottom = 0.dp)
        5 -> return PaddingValues(start = 4.dp, end = 0.dp, top = 4.dp, bottom = 4.dp)
        6 -> return PaddingValues(start = 0.dp, end = 4.dp, top = 4.dp, bottom = 0.dp)
        7 -> return PaddingValues(start = 2.dp, end = 2.dp, top = 4.dp, bottom = 0.dp)
        8 -> return PaddingValues(start = 4.dp, end = 0.dp, top = 4.dp, bottom = 0.dp)
    }
    return PaddingValues()
}