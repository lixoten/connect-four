// Stage 4/5: Winning Stage
package connectfour

import kotlin.system.exitProcess

const val PLAYER_ONE = 'o'
const val PLAYER_TWO = '*'
fun main() {
    val regexBoardSize = Regex("[0-9]?[0-9]X[0-9][0-9]?")

    println("Connect Four")
    println("First player's name:")
    var firstPlayer = readln()
    if (firstPlayer.isEmpty()) firstPlayer = "Ava"

    println("Second player's name:")
    var secondPlayer = readln()
    if (secondPlayer.isEmpty()) secondPlayer = "Oliver"

    var validBoardSize: Boolean
    var boardSize = ""
    var boardRows = 0
    var boardCols = 0
    do {
        println("Set the board dimensions (Rows x Columns)")
        println("Press Enter for default (6 x 7)")
        boardSize = readln().uppercase()
        if (boardSize.isEmpty()) boardSize = "6X7"

        boardSize = boardSize.replace(" ", "")
        boardSize = boardSize.replace("\t", "")
        if (regexBoardSize.matches(boardSize)) {
            validBoardSize = true
            boardRows = boardSize.substringBefore("X").toInt()
            boardCols = boardSize.substringAfter("X").toInt()
            if (boardRows !in 5..9) {
                println("Board rows should be from 5 to 9")
                validBoardSize = false
            } else if (boardCols !in 5..9) {
                println("Board columns should be from 5 to 9")
                validBoardSize = false
            } else {
                boardSize = boardSize.replace("X", " X ")
            }
        } else {
            println("Invalid input")
            validBoardSize = false
        }
    } while (!validBoardSize)

    val rowMove = 0
    val colMove = 0

    // BEG DEBUG
    //boardRows = 5
    //boardCols = 7

    var board = MutableList(boardRows) { MutableList(boardCols) { ' ' } }

    println("$firstPlayer VS $secondPlayer")
    println("$boardSize board")
    printBoardGame(board, boardRows, boardCols)

    val playerOne = 'o'
    val playerTwo = '*'
    var playersTurn = PLAYER_ONE
    var gameOn = true

    val validMove = Regex("[0-9]+")
    while (gameOn) {
        println(if (playersTurn == PLAYER_ONE) "$firstPlayer's turn:" else "$secondPlayer's turn:")
        val input = readln()

        if (input == "end") {
            println("Game over!")
            break
        }

        if (!validMove.matches(input)) {
            println("Incorrect column number")
            continue
        }

        val move = input.toInt()

        if (move == 0 || move >= board[0].size + 1) {
            println("The column number is out of range (1 - " + board[0].size + ")")
            continue
        }

        if (board[0][move - 1] != ' ' ) {
            println("Column $move is full")
            continue
        }

        // var move = 0
        // move = 4
        // var ch = 'm'
        for (r in boardRows-1 downTo 0) {
            if (board[r][move-1] == ' ') { // || r == boardRows-1
                board[r][move-1] = playersTurn

                break
            }
        }

        printBoardGame(board, boardRows, boardCols)

        val playerWon =  checkForWinner(board, playersTurn)

        if (!board[0].contains(' ')) {
            println("It is a draw")
            gameOn = false
            break
        }
        if (playerWon){
            if (playersTurn == PLAYER_ONE) {
                println("Player $firstPlayer won")
            } else {
                println("Player $secondPlayer won")
            }
            //print("Game over!")
            //println("Game overccc!")
            gameOn = false
            break
        }

        // Flip player
        playersTurn = if (playersTurn == PLAYER_ONE) PLAYER_TWO else PLAYER_ONE
    }
    //println()
    println("Game over!")
}

fun checkForWinner(board: MutableList<MutableList<Char>>, player: Char): Boolean{
    // Check Across
    for (row in 0..board.size - 1) {
        for (col in 0..board[0].size - 4) {
            if ((board[row][col] == player) &&
                (board[row][col + 1] == player) &&
                (board[row][col + 2] == player) &&
                (board[row][col + 3] == player))
                return true
        }
    }

    // Check Down
    for (row in 0..board.size - 4) {
        for (col in 0..board[0].size - 1) {
            if ((board[row][col] == player) &&
                (board[row + 1][col] == player) &&
                (board[row + 2][col] == player) &&
                (board[row + 3][col] == player))
                return true
        }
    }

    // Check downward Diagonal
    for (row in 0 until board.size - 3) {
        for (col in 0 until board[0].size - 3) {
            if ((board[row][col] == player) &&
                (board[row + 1][col + 1] == player) &&
                (board[row + 2][col + 2] == player) &&
                (board[row + 3][col + 3] == player))
                return true
        }
    }

    // Check upwards Diagonal
    for (row in 3 until board.size) {
        for (col in 0 until board[0].size - 3) {
            if ((board[row][col] == player) &&
                (board[row - 1][col + 1] == player) &&
                (board[row - 2][col + 2] == player) &&
                (board[row - 3][col + 3] == player))
                return true
        }
    }



    //check downward diagonal
    /*
    for (row in 0 until stateList.size - 3) { // - 4
        for (col in 0 until stateList[0].size - 3) { // - 4
            if (stateList[row][col] == player &&
                stateList[row + 1][col + 1] == player &&
                stateList[row + 2][col + 2] == player &&
                stateList[row + 3][col + 3] == player) {
                return true
            }
        }
    }
    */
    return false
}


fun printBoardGame(board: MutableList<MutableList<Char>>, boardRows: Int, boardCols:Int) {
    var bTop = ""
    var bLine = ""
    var bBot = ""
    for (n in 1..boardCols + 1) {
        if (n == 1) {
            bTop += " $n"
            bBot += "╚═"
        } else if (n == boardCols + 1) {
            bBot += "╝"
        } else if (n % 2 == 0){
            bTop += " $n"
            bBot += "╩═"
        } else {
            bTop += " $n"
            bBot += "╩═"
        }
    }

    println(bTop)
    for (r in 1..boardRows) {
        bLine = ""
        for (c in 1..boardCols + 1) {
            if (c == 1) {
                bLine += "║" + board[r-1][c-1]
            } else if (c == boardCols + 1) {
                bLine += "║"
            } else if (c % 2 == 0){
                bLine += "║" + board[r-1][c-1]
            } else {
                bLine += "║" + board[r-1][c-1]
            }
        }
        println(bLine)
    }
    println(bBot)
}