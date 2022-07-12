// Stage 2/5: Game board
package connectfour

fun main() {
    val regexBoardSize = Regex("[0-9]?[0-9]X[0-9][0-9]?")

    var gameOn = true
    println("Connect Four")
    println("First player's name:")
    var firstPlayer = readln()
    if (firstPlayer.isEmpty()) firstPlayer = "Ava"

    println("Second player's name:")
    var secondPlayer = readln()
    if (secondPlayer.isEmpty()) secondPlayer = "Oliver"

    var validBoardSize: Boolean
    var boardSize = ""
    var row = 0
    var col = 0
    do {
        println("Set the board dimensions (Rows x Columns)")
        println("Press Enter for default (6 x 7)")
        boardSize = readln().uppercase()
        if (boardSize.isEmpty()) boardSize = "6X7"

        boardSize = boardSize.replace(" ", "")
        boardSize = boardSize.replace("\t", "")
        if (regexBoardSize.matches(boardSize)) {
            validBoardSize = true
            row = boardSize.substringBefore("X").toInt()
            col = boardSize.substringAfter("X").toInt()
            if (row !in 5..9) {
                println("Board rows should be from 5 to 9")
                validBoardSize = false
            } else if (col !in 5..9) {
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

    println("$firstPlayer VS $secondPlayer")
    println("$boardSize board")
    println()

    var bTop = ""
    var bLine = ""
    var bBot = ""
    var x = 0
    for (n in 1..col + 1) {
        x++
        if (n == 1) {
            bTop += " $n"
            bLine += "║ "
            bBot += "╚═"
        } else if (n == col + 1) {
            bLine += "║"
            bBot += "╝"
        } else if (n % 2 == 0){
            bTop += " $n"
            bLine += "║ "
            bBot += "╩═"
        } else {
            bTop += " $n"
            bLine += "║ "
            bBot += "╩═"
        }
    }

    println(bTop)
    for (n in 1..row) {
        println(bLine)
    }
    println(bBot)
}