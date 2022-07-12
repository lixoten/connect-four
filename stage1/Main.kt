package connectfour

fun main() {

    val regexBoardSize = Regex("[0-9]?[0-9]X[0-9][0-9]?")

    //..var cnt = 0

    //return
    var gameOn = true
    //..while (gameOn) {
        println("Connect Four")
        println("First player's name:")
        var firstPlayer = readln()
        if (firstPlayer.isEmpty()) firstPlayer = "Ava"

        println("Second player's name:")
        var secondPlayer = readln()
        if (secondPlayer.isEmpty()) secondPlayer = "Oliver"


        var validBoardSize = false
        var boardSize = ""
        do {
            println("Set the board dimensions (Rows x Columns)")
            println("Press Enter for default (6 x 7)")
            boardSize = readln().uppercase()
            if (boardSize.isEmpty()) boardSize = "6X7"

            var row = 0
            var col = 0

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

            //.. cnt++
        } while (!validBoardSize)


        println("$firstPlayer VS $secondPlayer")
        println("$boardSize board")
        println()
    //..}
}