package com.example.tictactoe

class TicTacToe {
    private var board = Array(SIZE) {
        IntArray(
            SIZE
        )
    }
    var playersTurn = -1 //Player X = -1, Player O = 1
        private set

    fun setMove(i: Int, j: Int): Boolean {
        if (board[i][j] == 0 && gameState == 2) {
            board[i][j] = playersTurn
            playersTurn = -playersTurn
            return true
        }
        return false
    }

    /**
     * @return -1 if X won, 0 if drawn, 1 if O won, 2 if not ended
     */
    val gameState: Int
        get() {
            if (checkIfPlayerWon(-1)) return -1
            if (checkIfPlayerWon(1)) return 1
            var isDraw = true
            for (i in 0 until SIZE) {
                for (j in 0 until SIZE) {
                    if (board[i][j] == 0) {
                        isDraw = false
                        break
                    }
                }
            }
            return if (isDraw) 0 else 2
        }

    private fun checkIfPlayerWon(p: Int): Boolean {
        //check rows
        for (i in 0 until SIZE) {
            var isWin = true
            for (j in 0 until SIZE) {
                if (board[i][j] != p) {
                    isWin = false
                    break
                }
            }
            if (isWin) return true
        }

        //check columns
        for (j in 0 until SIZE) {
            var isWin = true
            for (i in 0 until SIZE) {
                if (board[i][j] != p) {
                    isWin = false
                    break
                }
            }
            if (isWin) return true
        }

        //check diagonals
        var isWin = true
        for (i in 0 until SIZE) {
            if (board[i][i] != p) {
                isWin = false
                break
            }
        }
        if (isWin) return true
        isWin = true
        for (i in 0 until SIZE) {
            if (board[i][SIZE - i - 1] != p) {
                isWin = false
                break
            }
        }
        return isWin
    }

    fun reset() {
        board = Array(SIZE) {
            IntArray(
                SIZE
            )
        }

        playersTurn = -1
    }

    // AI METHODS //
    val aIMove: IntArray
        get() {
            val bestMove = IntArray(2)
            var bestScore = if (playersTurn == -1) 2 else -2
            for (i in 0 until SIZE) {
                for (j in 0 until SIZE) {
                    if (board[i][j] == 0) {
                        board[i][j] = playersTurn
                        val score = minimax(playersTurn == -1, 5, Int.MIN_VALUE, Int.MAX_VALUE)
                        if (playersTurn == 1) {
                            if (score > bestScore) {
                                bestScore = score
                                bestMove[0] = i
                                bestMove[1] = j
                            }
                        } else {
                            if (score < bestScore) {
                                bestScore = score
                                bestMove[0] = i
                                bestMove[1] = j
                            }
                        }
                        board[i][j] = 0
                    }
                }
            }
            return bestMove
        }

    //same minimax with alpha-beta pruning
    private fun minimax(isMaximizing: Boolean, depth: Int, alpha: Int, beta: Int): Int {
        var alpha = alpha
        var beta = beta
        val state = gameState
        if (state != 2) return state
        if (depth == 0) return 0
        var bestScore = if (isMaximizing) -2 else 2
        for (i in 0 until SIZE) {
            for (j in 0 until SIZE) {
                if (board[i][j] == 0) {
                    if (isMaximizing) {
                        board[i][j] = 1
                        val score = minimax(false, depth - 1, alpha, beta)
                        bestScore = bestScore.coerceAtLeast(score)
                        alpha = alpha.coerceAtLeast(bestScore)
                        //System.out.println("Max " + depth + ": " + bestScore);
                    } else {
                        board[i][j] = -1
                        val score = minimax(true, depth - 1, alpha, beta)
                        bestScore = bestScore.coerceAtMost(score)
                        beta = beta.coerceAtMost(bestScore)
                        //System.out.println("Min " + depth + ": " + bestScore);
                    }
                    board[i][j] = 0
                }
                if (beta <= alpha) {
                    return bestScore
                }
            }
        }
        return bestScore
    }

    companion object {
        var SIZE = 3
    }
}