package com.example.tictactoe.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BoxInGrid(
    paddingValues: PaddingValues,
    enabled: Boolean,
    gameGrid: SnapshotStateMap<Int, Char>,
    index: Int,
    color: Color,
    onBoxClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .background(color)
            .aspectRatio(1f)
            .fillMaxWidth()
            .height(0.dp)
            .clickable(enabled = enabled && gameGrid[index] == Char.MIN_VALUE) {
                onBoxClick(index)
            }, contentAlignment = Alignment.Center
    ) {
        Text(
            text = gameGrid[index].toString(), fontSize = 60.sp, textAlign = TextAlign.Center
        )
    }
}