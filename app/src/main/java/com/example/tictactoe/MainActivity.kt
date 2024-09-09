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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
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

fun initMap(map: SnapshotStateMap<Int, Char>) {
    map[0] = Char.MIN_VALUE
    map[1] = Char.MIN_VALUE
    map[2] = Char.MIN_VALUE
    map[3] = Char.MIN_VALUE
    map[4] = Char.MIN_VALUE
    map[5] = Char.MIN_VALUE
    map[6] = Char.MIN_VALUE
    map[7] = Char.MIN_VALUE
    map[8] = Char.MIN_VALUE
}

val indexes = mutableListOf(
    listOf(0, 1, 2),
    listOf(3, 4, 5),
    listOf(6, 7, 8),
    listOf(0, 4, 8),
    listOf(2, 4, 6),
    listOf(0, 3, 6),
    listOf(1, 4, 7),
    listOf(2, 5, 8)
)

fun checkIfGameEnded(map: SnapshotStateMap<Int, Char>): Boolean {
    var i1: Int
    var i2: Int
    var i3: Int
    var val1: Char
    var val2: Char
    var val3: Char

    for (i in indexes) {
        i1 = i[0]; i2 = i[1]; i3 = i[2]
        val1 = map[i1]!!; val2 = map[i2]!!; val3 = map[i3]!!

        if (val1 != Char.MIN_VALUE && val2 != Char.MIN_VALUE && val3 != Char.MIN_VALUE && val1 == val2 && val2 == val3) {
            return true
        }
    }

    return false
}

@Composable
fun TicTacToeApp(modifier: Modifier = Modifier) {
    var player by remember { mutableStateOf(true) }
    var gameEnded by remember { mutableStateOf(false) }
    val map: SnapshotStateMap<Int, Char> = remember {
        mutableStateMapOf(
            0 to Char.MIN_VALUE,
            1 to Char.MIN_VALUE,
            2 to Char.MIN_VALUE,
            3 to Char.MIN_VALUE,
            4 to Char.MIN_VALUE,
            5 to Char.MIN_VALUE,
            6 to Char.MIN_VALUE,
            7 to Char.MIN_VALUE,
            8 to Char.MIN_VALUE
        )
    }

    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = {
            initMap(map)
            gameEnded = false
            player = true
        }, modifier = Modifier.background(Color.Magenta).height(60.dp).width(60.dp)) {
            Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Restart game")
        }
        if (gameEnded) {
            Text(text = "POBJEDA", fontSize = 80.sp)
        } else {
            repeat(3) { i ->
                RowInGrid(index = i, onClick = {
                    if (map[it.first] == Char.MIN_VALUE) {
                        map[it.first] = it.second
                        player = !player
                    }
                    gameEnded = checkIfGameEnded(map)
                }, modifier = modifier, map = map, player = player)
            }
        }
    }
}

@Composable
fun RowInGrid(
    modifier: Modifier = Modifier,
    index: Int,
    map: SnapshotStateMap<Int, Char>,
    onClick: (Pair<Int, Char>) -> Unit,
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
    onClick: (Pair<Int, Char>) -> Unit,
    map: SnapshotStateMap<Int, Char>,
    player: Boolean
) {
    val text = if (player) {
        'X'
    } else {
        'O'
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
        Text(text = map[index]!!.toString(), fontSize = 40.sp, textAlign = TextAlign.Center)
    }
}

@Composable
@Preview
fun Preview() {
    TicTacToeApp()
}