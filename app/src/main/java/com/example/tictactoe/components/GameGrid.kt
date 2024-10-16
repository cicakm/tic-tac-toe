package com.example.tictactoe.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.tictactoe.utils.calculatePadding

@Composable
fun GameGrid(
    modifier: Modifier,
    gameGrid: SnapshotStateMap<Int, Char>,
    winnersList: List<Int>,
    enabled: Boolean,
    changeable: Int,
    onBoxClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray),
        verticalArrangement = Arrangement.Center
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.background(Color.Black),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            items(9) { index ->
                var color = if (changeable == index) Color.Red else Color.White
                if (winnersList.contains(index)) {
                    color = Color.Green
                }
                val paddingValues = calculatePadding(index)
                BoxInGrid(
                    paddingValues = paddingValues,
                    enabled = enabled,
                    gameGrid = gameGrid,
                    index = index,
                    color = color,
                    onBoxClick = onBoxClick
                )
            }
        }
    }
}