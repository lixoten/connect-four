// Stage 5/5: Multiple games
package connectfour

import kotlin.system.exitProcess

const val PLAYER_ONE = 'o'
const val PLAYER_TWO = '*'

class ConnectFour {
    var playerCount = 0
    var firstPlayer = ""
    var secondPlayer = ""
    var playersTurn = PLAYER_TWO
    var boardSize = ""
    var boardRows = 0
    var boardCols = 0
    var numberOfGames = 0
    var gameCounter = 0
    var firstPlayerWinCnt = 0
    var secondPlayerWinCnt = 0

    var board = mutableListOf<MutableList<Char>>()

    init {
        println("Connect Four")
    }

    fun setupPlayers(gameObj: ConnectFour) {
        println("First player's name:")
        gameObj.getPlayer()
        println("Second player's name:")
        gameObj.getPlayer()
    }

    fun getPlayer(): String {
        playerCount++
        val str = readln()

        if (playerCount == 1) {
            firstPlayer = if (str == "") "Anna" else str
        } else {
            secondPlayer = if (str == "") "Joan" else str
        }
        return str
    }

    fun setupBoard(dftSize: String = "6X7"): String {
        val regexBoardSize = Regex("[0-9]?[0-9]X[0-9][0-9]?")
        var validBoardSize: Boolean

        do {
            println("Set the board dimensions (Rows x Columns)")
            println("Press Enter for default (6 x 7)")
            boardSize = readln().uppercase()
            if (boardSize.isEmpty()) boardSize = dftSize

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

        board = MutableList(boardRows) { MutableList(boardCols) { ' ' } }

        return boardSize
    }

    fun playGame(){
        //var gameOn = true

        // Flip player initial Flip between games. Alternative move first player move
        playersTurn = if (playersTurn == PLAYER_ONE) PLAYER_TWO else PLAYER_ONE

        val validMove = Regex("[0-9]+")
        while (true) {
            println(if (playersTurn == PLAYER_ONE) "$firstPlayer's turn:" else "$secondPlayer's turn:")
            val input = readln()

            if (input == "end") {
                exitGame() // Manually typed in "end"
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

            if (board[0][move - 1] != ' ') {
                println("Column $move is full")
                continue
            }

            for (r in boardRows-1 downTo 0) {
                if (board[r][move-1] == ' ') {
                    board[r][move-1] = playersTurn
                    break
                }
            }

            printBoardGame(board)

            val playerWon = checkForWinner(board, playersTurn)

            if (!board[0].contains(' ')) {
                println("It is a draw")
                firstPlayerWinCnt += 1
                secondPlayerWinCnt += 1
                board = MutableList(boardRows) { MutableList(boardCols) { ' ' } }
                break
            }
            if (playerWon) {
                if (playersTurn == PLAYER_ONE) {
                    println("Player $firstPlayer won")
                    firstPlayerWinCnt += 2
                } else {
                    println("Player $secondPlayer won")
                    secondPlayerWinCnt += 2
                }
                board = MutableList(boardRows) { MutableList(boardCols) { ' ' } }
                break
            }

            // Flip player everytime between move
            playersTurn = if (playersTurn == PLAYER_ONE) PLAYER_TWO else PLAYER_ONE
        }
    }


    fun printBoardGame(board: MutableList<MutableList<Char>>) {
        var bTop = ""
        //var bLine: String
        var bBot = ""
        for (n in 1..boardCols + 1) {
            if (n == 1) {
                bTop += " $n"
                bBot += "╚═"
            } else if (n == boardCols + 1) {
                bBot += "╝"
            } else if (n % 2 == 0) {
                bTop += " $n"
                bBot += "╩═"
            } else {
                bTop += " $n"
                bBot += "╩═"
            }
        }

        println(bTop)
        for (r in 1..boardRows) {
            var bLine = ""
            for (c in 1..boardCols + 1) {
                bLine += if (c == 1) {
                    "║" + board[r-1][c-1]
                } else if (c == boardCols + 1) {
                    "║"
                } else if (c % 2 == 0) {
                    "║" + board[r-1][c-1]
                } else {
                    "║" + board[r-1][c-1]
                }
            }
            println(bLine)
        }
        println(bBot)
    }

    fun checkForWinner(board: MutableList<MutableList<Char>>, player: Char): Boolean{
        // Check Across
        for (row in 0 until board.size) {
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
            for (col in 0 until board[0].size) {
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

        return false
    }

    fun numberOfGamesSetup(defaultNumberOfGames: Int = 1) {
        do {
            println("Do you want to play single or multiple games?")
            println("For a single game, input 1 or press Enter")
            println("Input a number of games: ")
            val input = readln()
            if (input.isEmpty()) {
                numberOfGames = defaultNumberOfGames
            } else {
                val regexGamesNumber = Regex("[1-9][0-9]*")
                if (regexGamesNumber.matches(input)) {
                    numberOfGames = input.toInt()
                } else {
                    println("Invalid input")
                    continue
                }
                if (numberOfGames < 1) numberOfGames = defaultNumberOfGames
            }

            break
        } while (true)
    }

    fun initDisplay() {
        println("$firstPlayer VS $secondPlayer")
        println("$boardSize board")

        when {
            numberOfGames == 1 ->{
                println("Single game")
                printBoardGame(board)
            }
            numberOfGames > 1 -> {
                println("Total $numberOfGames games")
                gameCounter++
                println("Game #$gameCounter")
                printBoardGame(board)
            }
        }
    }

    fun exitGame() {
        println("Game over!")
        exitProcess(0)
    }

    fun displayStats() {
        println("Score")
        print("$firstPlayer: $firstPlayerWinCnt")
        println(" $secondPlayer: $secondPlayerWinCnt")
    }
}


fun main() {

    val gameObj = ConnectFour()

    gameObj.setupPlayers(gameObj)

    gameObj.setupBoard("6X7")

    gameObj.numberOfGamesSetup(1)

    gameObj.initDisplay()

    repeat(gameObj.numberOfGames) {
        gameObj.playGame()

        if (gameObj.numberOfGames > 1 && gameObj.gameCounter < gameObj.numberOfGames) {
            gameObj.displayStats()
            gameObj.gameCounter++
            println("Game #${gameObj.gameCounter}")
            gameObj.printBoardGame(gameObj.board)
        }
    }

    gameObj.displayStats()
    gameObj.exitGame()
}
// 318 332 250