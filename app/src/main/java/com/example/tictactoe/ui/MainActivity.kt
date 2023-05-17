package com.example.tictactoe.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tictactoe.TicTacToeViewModel
import com.example.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeTheme {
                val viewModel = viewModel<TicTacToeViewModel>()

                ShowWarningDialog(viewModel = viewModel, showWarning = viewModel.showWarning.collectAsStateWithLifecycle().value)

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = viewModel.gameInfo.collectAsStateWithLifecycle().value,
                            style = MaterialTheme.typography.displayLarge
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        TicTacToeBoard(
                            board = viewModel.board.collectAsStateWithLifecycle().value,
                            onMoveMade = { i, j -> viewModel.setMove(i, j) }
                        )
                        Spacer(modifier = Modifier.height(80.dp))
                        Row {
                            Button(
                                shape = MaterialTheme.shapes.small,
                                onClick = { viewModel.makeAIMoveWithWarning() }
                            ) {
                                Text(text = "AI Move")
                            }
                            Spacer(modifier = Modifier.width(20.dp))
                            Button(
                                shape = MaterialTheme.shapes.small,
                                onClick = { viewModel.resetGame() }
                            ) {
                                Text(text = "Reset")
                            }
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(text = "Board size", style = MaterialTheme.typography.labelMedium)
                            Slider(
                                modifier = Modifier.width(300.dp),
                                value = viewModel.boardSize.collectAsStateWithLifecycle().value.toFloat(),
                                onValueChange = { newSize -> viewModel.setBoardSize(newSize.toInt()) },
                                valueRange = 2f..9f,
                                steps = 6
                            )
                            Row() {
                                Text(text = "2")
                                for (i in 3..9) {
                                    Spacer(modifier = Modifier.width(300.dp / 10))
                                    Text(text = i.toString())
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowWarningDialog(viewModel: TicTacToeViewModel, showWarning: Boolean) {
    if (!showWarning) return
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Warning!") },
        text = {
            Column {
                Text(
                    text = "Making an AI move on a board of size greater than 5 may take a very long time.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Do you want to continue?",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = !viewModel.shouldShowWarning.collectAsStateWithLifecycle().value,
                        onCheckedChange = { viewModel.toggleWarning()}
                    )
                    Text(
                        text = "Do not show again",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.aIMove()
                    viewModel.setShowWarning(false)
                }
            ) {
                Text(text = "Continue")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    viewModel.setShowWarning(false)
                }
            ) {
                Text(text = "Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        TicTacToeTheme {

        }
    }
