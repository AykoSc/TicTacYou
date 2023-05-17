package com.example.tictactoe.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoe.TicTacToe

@Composable
fun TicTacToeBoard(board: Array<Array<String>>, onMoveMade: (row: Int, column: Int) -> Unit) {
    for (i in 0 until TicTacToe.SIZE) {
        Row {
            for (j in 0 until TicTacToe.SIZE) {
                Box(
                    modifier = Modifier
                        .size(300.dp / TicTacToe.SIZE)
                        .border(2.dp, MaterialTheme.colorScheme.inversePrimary)
                        .clickable { onMoveMade(i, j) }
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = board[i][j],
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 220.sp / TicTacToe.SIZE,
                            color = MaterialTheme.colorScheme.inversePrimary,
                            fontWeight = FontWeight.W900
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}