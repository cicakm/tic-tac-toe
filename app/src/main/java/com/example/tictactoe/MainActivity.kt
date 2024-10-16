package com.example.tictactoe

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.tictactoe.components.GameGrid
import com.example.tictactoe.ui.theme.TicTacToeTheme
import com.example.tictactoe.utils.checkIfGameEnded
import com.example.tictactoe.utils.fillMaps
import com.example.tictactoe.utils.initMap
import com.example.tictactoe.utils.nextInChange
import com.example.tictactoe.utils.winnerIndexes

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
            'x' to listOf(), 'o' to listOf()
        )
    }
    var winnersList = remember { mutableListOf<Int>() }

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Tic Tac Toe 2.0") }, actions = {
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
        GameGrid(modifier = Modifier.padding(innerPadding),
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
                    winnersList = winnerIndexes(player, mappedGame)
                }
            })
    }
}

@Composable
@Preview
fun Preview() {
    TicTacToeApp()
}