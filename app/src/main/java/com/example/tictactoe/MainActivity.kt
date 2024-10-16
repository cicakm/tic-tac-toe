package com.example.tictactoe

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.window.Dialog
import com.example.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()
        setContent {
            TicTacToeTheme {
                TicTacToeApp()
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

    var index1: Int; var index2: Int; var index3: Int
    var value1: Char; var value2: Char; var value3: Char

    for (indexes in gameWinners) {
        index1 = indexes[0]; index2 = indexes[1]; index3 = indexes[2]
        value1 = map[index1]!!; value2 = map[index2]!!; value3 = map[index3]!!

        if (value1 != Char.MIN_VALUE &&
            value2 != Char.MIN_VALUE &&
            value3 != Char.MIN_VALUE &&
            value1 == value2
            && value2 == value3
        ) {
            return true
        }
    }

    return false
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

fun fillMaps(
    map: SnapshotStateMap<Int, Char>,
    mappedGame: SnapshotStateMap<Char, List<Int>>,
    index: Int,
    player: Boolean
) {
    val text = if (player) 'x' else 'o'
    if (mappedGame['o']!!.size < 3 || mappedGame['x']!!.size < 3) {
        mappedGame[text] = mappedGame[text]!!.plus(index)
        map[index] = text
    } else {
        map[mappedGame[text]!!.first()] = Char.MIN_VALUE
        map[index] = text
        mappedGame[text] = mappedGame[text]!!.drop(1).plus(index)
    }
}

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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTacToeApp() {
    var player by remember { mutableStateOf(true) }
    var enabled by remember { mutableStateOf(true) }
    var gameEnded by remember { mutableStateOf(false) }
    val gameGrid: SnapshotStateMap<Int, Char> = remember { mutableStateMapOf() }
    initMap(gameGrid)
    var changeable by remember { mutableIntStateOf(-1) }
    val mappedGame: SnapshotStateMap<Char, List<Int>> = remember {
        mutableStateMapOf(
            'x' to listOf(),
            'o' to listOf()
        )
    }
    var winnersList = remember { mutableListOf<Int>() }

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Tic Tac Toe 2.0") },
            actions = {
                IconButton(onClick = {
                    initMap(gameGrid)
                    gameEnded = false
                    player = true
                    enabled = true
                    changeable = -1
                    mappedGame['x'] = listOf()
                    mappedGame['o'] = listOf()
                    winnersList = mutableListOf()
                }) {
                    Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Restart game")
                }
            })
    }, modifier = Modifier.fillMaxSize()) { innerPadding ->
        if (gameEnded) {
            Dialog(onDismissRequest = {
                gameEnded = false
                enabled = false
            }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Text(
                        text = "Pobijedio je igrač ${if (player) 'X' else 'O'}!!",
                        fontSize = 24.sp,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
        GameGrid(
            modifier = Modifier.padding(innerPadding),
            gameGrid = gameGrid,
            winnersList = winnersList,
            enabled = enabled,
            changeable = changeable,
            onBoxClick = { index ->
                fillMaps(gameGrid, mappedGame, index, player)
                gameEnded = checkIfGameEnded(gameGrid)
                if (!gameEnded) {
                    changeable = nextInChange(mappedGame, player)
                    player = !player
                } else {
                    changeable = -1
                    winnersList =
                        if (player) {
                            mappedGame['x']!!.toMutableList()
                        } else {
                            mappedGame['o']!!.toMutableList()
                        }
                }
            }
        )
    }
}

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
            .background(Color.LightGray), verticalArrangement = Arrangement.Center
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .background(Color.Black),
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
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = gameGrid[index].toString(),
            fontSize = 60.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun Preview() {
    TicTacToeApp()
}