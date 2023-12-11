import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}


class Day10 {

    enum class Token(val value: Char) {
        HORIZONTAL('-'),
        VERTICAL('|'),
        NORTH_EAST('L'),
        NORTH_WEST('J'),
        SOUTH_EAST('F'),
        SOUTH_WEST('7'),
        GROUND('.'),
        START('S'),
        VISITED('V'),
        OUT_OF_BOUNDS('O'),
    }

    class Walker(val map: List<MutableList<Token>>) {
        var column = 0
        var row = 0
        var stepCount = 0.0
        fun moveToStart(): Pair<Int, Int> {
            stepCount = 0.0
            map.forEachIndexed { y, line ->
//                println("line: $line, y: $y")
                line.forEachIndexed { x, token ->
//                    println("token: $token, x: $x")
                    if (token == Token.START) {
                        println("found start at x (column): $x, y (row): $y")
                        column = x
                        row = y
                    }
                }
            }
            return row to column
        }

        fun peekTokenOrBlankAt(r: Int, c: Int): Token {
            println("peekTokenOrBlankAt($r, $c)")
            return if (r < 0 || r >= map.size || c < 0 || c >= map[r].size) {
                Token.OUT_OF_BOUNDS
            } else {
                map[r][c]
            }
        }

        fun isGoodNextStep(token: Token): Boolean {
            return token !in listOf(Token.GROUND, Token.VISITED)
        }

        fun step(): Token {
            println("step $stepCount: $current at row: $row, column: $column")
            stepCount++
            val previous = current
            val previousRow = row
            val previousColumn = column
            val movedTo = when (current) {
                Token.HORIZONTAL -> horizontal()
                Token.VERTICAL -> vertical()
                Token.NORTH_EAST -> northEast()
                Token.NORTH_WEST -> northWest()
                Token.SOUTH_EAST -> southEast()
                Token.SOUTH_WEST -> southWest()
                Token.START -> northSouthEastWest()
                else -> throw IllegalStateException("no ground to step on")
            }
            map[previousRow][previousColumn] = Token.VISITED
            return movedTo
        }

        fun northSouthEastWest(): Token {
            println("northSouthEastWest")
            if (peekTokenOrBlankAt(row - 1, column).let(::isGoodNextStep)) {
                return north()
            } else if (peekTokenOrBlankAt(row + 1, column).let(::isGoodNextStep)) {
                return south()
            } else if (peekTokenOrBlankAt(row, column + 1).let(::isGoodNextStep)) {
                return east()
            } else if (peekTokenOrBlankAt(row, column - 1).let(::isGoodNextStep)) {
                return west()
            } else {
                throw IllegalStateException("no ground to step on")
            }
        }

        fun southWest(): Token {
            if (peekTokenOrBlankAt(row + 1, column).let(::isGoodNextStep)) {
                return south()
            } else if (peekTokenOrBlankAt(row, column - 1).let(::isGoodNextStep)) {
                return west()
            } else {
                throw IllegalStateException("no ground to step on")
            }
        }

        fun southEast(): Token {
            if (peekTokenOrBlankAt(row + 1, column).let(::isGoodNextStep)) {
                return south()
            } else if (peekTokenOrBlankAt(row, column + 1).let(::isGoodNextStep)) {
                return east()
            } else {
                throw IllegalStateException("no ground to step on")
            }
        }

        fun northWest(): Token {
            if (peekTokenOrBlankAt(row - 1, column).let(::isGoodNextStep)) {
                return north()
            } else if (peekTokenOrBlankAt(row, column - 1).let(::isGoodNextStep)) {
                return west()
            } else {
                throw IllegalStateException("no ground to step on")
            }
        }

        fun northEast(): Token {
            if (peekTokenOrBlankAt(row - 1, column).let(::isGoodNextStep)) {
                return north()
            } else if (peekTokenOrBlankAt(row, column + 1).let(::isGoodNextStep)) {
                return east()
            } else {
                throw IllegalStateException("no ground to step on")
            }
        }

        fun horizontal(): Token {
            if (peekTokenOrBlankAt(row, column + 1).let(::isGoodNextStep)) {
                return east()
            } else if (peekTokenOrBlankAt(row, column - 1).let(::isGoodNextStep)) {
                return west()
            } else {
                throw IllegalStateException("no ground to step on")
            }
        }

        fun vertical(): Token {
            if (peekTokenOrBlankAt(row - 1, column).let(::isGoodNextStep)) {
                return north()
            } else if (peekTokenOrBlankAt(row + 1, column).let(::isGoodNextStep)) {
                return south()
            } else {
                throw IllegalStateException("no ground to step on")
            }
        }

        fun walkLoop(): Double {
            try {
                do {
                    step()
                } while (current != Token.START)
            } catch (e: IllegalStateException) {
                println("caught exception: $e")
                println(e.stackTraceToString())
            }
            return stepCount
        }

        val current: Token
            get() = map[row][column]

        fun north(): Token {
            row--
            return current
        }

        fun south(): Token {
            row++
            return current
        }

        fun east(): Token {
            column++
            return current
        }

        fun west(): Token {
            column--
            return current
        }
    }

    fun parse(input: String): List<MutableList<Token>> {
        return input.trim().split("\n").map { line ->
            line.map {
                Token.entries.find { token -> token.value == it }!!
            }.toMutableList()
        }
    }

    fun renderAsDrawing(input: String): String = input
        .replace("L", "┗")
        .replace("J", "┛")
        .replace("7", "┓")
        .replace("F", "┏")
        .replace("|", "┃")
        .replace("-", "━")


    fun solution01(input: String): Double {
        val walker = parse(input).let(::Walker)
        walker.moveToStart()
        val steps = walker.walkLoop()
        return steps / 2
    }

    fun solution02(input: String): Int {
        return 0
    }
}

fun main() {
    val input = Utils.getResource("day10.txt")
    val today = Day10()
    val solution01 = today.solution01(input)
    val solution02 = today.solution02(input)
    println("Solution 01: $solution01")
    println("Solution 02: $solution02")
}