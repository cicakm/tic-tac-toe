package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TicTacToeApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TicTacToeApp(modifier: Modifier = Modifier) {
    var player by remember { mutableStateOf(true) }
    val map = remember {
        mutableMapOf(
            0 to "",
            1 to "",
            2 to "",
            3 to "",
            4 to "",
            5 to "",
            6 to "",
            7 to "",
            8 to ""
        )
    }
    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        repeat(3) { i ->
            RowInGrid(index = i, onClick = {
                map[it.first] = it.second
                player = !player
            }, modifier = modifier, map = map, player = player)
        }
    }

}

@Composable
fun RowInGrid(
    modifier: Modifier = Modifier,
    index: Int,
    map: Map<Int, String>,
    onClick: (Pair<Int, String>) -> Unit,
    player: Boolean
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        repeat(3) { i ->
            BoxInGrid(
                index = i + index * 3,
                onClick = onClick,
                modifier = modifier,
                map = map,
                player = player
            )
        }
    }
}

@Composable
fun BoxInGrid(
    modifier: Modifier = Modifier,
    index: Int,
    onClick: (Pair<Int, String>) -> Unit,
    map: Map<Int, String>,
    player: Boolean
) {
    val text = if (player) {
        "X"
    } else {
        "O"
    }
    Box(
        modifier = modifier
            .background(Color.LightGray)
            .height(80.dp)
            .width(80.dp)
            .clickable {
                onClick(Pair(index, text))
            }, contentAlignment = Alignment.Center
    ) {
        Text(text = map[index]!!, fontSize = 40.sp, textAlign = TextAlign.Center)
    }
}

@Composable
@Preview
fun Preview() {
    TicTacToeApp()
}