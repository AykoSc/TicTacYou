package com.example.tictactoe

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TicTacToeViewModel : ViewModel() {

    private val ttt = TicTacToe()

    private val _board = MutableStateFlow(Array(TicTacToe.SIZE) { Array(TicTacToe.SIZE) { "" } })
    val board = _board.asStateFlow()

    private val _gameInfo = MutableStateFlow("Turn: X")
    val gameInfo = _gameInfo.asStateFlow()

    private val _boardSize = MutableStateFlow(TicTacToe.SIZE)
    val boardSize = _boardSize.asStateFlow()

    private val _shouldShowWarning = MutableStateFlow(true)
    val shouldShowWarning = _shouldShowWarning.asStateFlow()

    private val _showWarning = MutableStateFlow(false)
    val showWarning = _showWarning.asStateFlow()

    private fun getPlayerAsSymbol(player: Int): String {
        return if (player == 1) "O" else "X"
    }

    private fun previousPlayer(): Int {
        return if (ttt.playersTurn == 1) 2 else 1
    }

    private fun moveSucceeded(i: Int, j: Int) {
        _board.value = _board.value.copyOf().apply {
            this[i][j] = getPlayerAsSymbol(previousPlayer())
        }

        when (ttt.gameState) {
            2 -> {
                _gameInfo.value = "Turn: " + getPlayerAsSymbol(ttt.playersTurn)
            }
            1, -1 -> {
                _gameInfo.value = getPlayerAsSymbol(previousPlayer()) + " won!"
            }
            0 -> {
                _gameInfo.value = "Draw!"
            }
        }
    }

    fun setMove(i: Int, j: Int): Boolean {
        if (ttt.setMove(i, j)) {
            moveSucceeded(i, j)
            return true
        }
        return false
    }

    fun setBoardSize(boardSize: Int) {
        if (boardSize == TicTacToe.SIZE || boardSize < 2 || boardSize > 9) return

        TicTacToe.SIZE = boardSize

        resetGame()

        _boardSize.value = TicTacToe.SIZE
    }

    fun setShowWarning(showWarning: Boolean) {
        _showWarning.value = showWarning
    }

    fun toggleWarning() {
        _shouldShowWarning.value = !_shouldShowWarning.value
    }

    fun makeAIMoveWithWarning() {
        if (TicTacToe.SIZE > 5 && _shouldShowWarning.value) {
            // Show warning dialog
            _showWarning.value = true
        } else {
            aIMove()
        }
    }

    fun aIMove() {
        val move = ttt.aIMove

        if (ttt.setMove(move[0], move[1])) {
            moveSucceeded(move[0], move[1])
        }
    }

    fun resetGame() {
        ttt.reset()

        _board.value = Array(TicTacToe.SIZE) { Array(TicTacToe.SIZE) { "" } }
        _gameInfo.value = "Turn: X"
    }
}