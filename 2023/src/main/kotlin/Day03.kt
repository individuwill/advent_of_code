import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}


open class SchematicSequence(val sequence: String, val row: Int, val startColumn: Int) {
    val endColumn: Int = startColumn + sequence.length - 1
    val allCoordinates: List<Pair<Int, Int>>
        get() {
            return (startColumn..endColumn).map { Pair(row, it) }
        }
    val borderCoordinates: List<Pair<Int, Int>>
        get() {
            return listOf(
                Pair(row, startColumn - 1), // left column
                Pair(row, endColumn + 1), // right column
            ) + (startColumn - 1..endColumn + 1).map { // top row
                Pair(
                    row - 1,
                    it
                )
            } + (startColumn - 1..endColumn + 1).map { Pair(row + 1, it) } // bottom row
        }

    fun isTouching(other: SchematicSequence): Boolean {
        return other.borderCoordinates.any { allCoordinates.contains(it) }
    }

    override fun toString(): String {
        return "SchematicSequence('$sequence', $row, $startColumn, $endColumn)"
    }
}

class SchematicSymbol(sequence: String, row: Int, startColumn: Int) : SchematicSequence(sequence, row, startColumn) {

}

class SchematicNumber(sequence: String, row: Int, startColumn: Int) : SchematicSequence(sequence, row, startColumn) {
    val number: Int = sequence.toInt()

}

fun parseInput(input: String) {
    Regex("""\d+""").findAll(input).forEach {
        logger.info { "Found number: ${it.value}" }
    }
}

class Schematic(val input: String) {
    val numbers: List<SchematicNumber> = parseForNumbers(input)
    val symbols: List<SchematicSymbol> = parseForSymbols(input)

    val symbolBorders: Set<Pair<Int, Int>>
        get() {
            return symbols.map {
                it.borderCoordinates
            }.flatten().toSortedSet() { a, b ->
                if (a.first == b.first) {
                    a.second - b.second
                } else {
                    a.first - b.first
                }
            }
        }
    val hitNumbers: List<SchematicNumber>
        get() {
            return numbers.filter { schematicNumber ->
                schematicNumber.allCoordinates.any { symbolBorders.contains(it) }
            }.sortedBy { it.number }
        }
    val hitSum = hitNumbers.sumOf { it.number }

    val touchedNumbers: List<SchematicNumber>
        get() {
            return numbers.filter { schematicNumber ->
                symbols.any { it.isTouching(schematicNumber) }
            }.sortedBy { it.number }
        }


    val gears: List<SchematicSymbol>
        get() {
            return symbols.filter {
                it.sequence == "*"
            }.filter {
                numbers.filter { number ->
                    number.isTouching(it)
                }.count() == 2
            }
        }

    val gearSum: Int
        get() {
            val gearProducts: List<Int> = gears.map { gear ->
                val touchedNumbers = numbers.filter { number ->
                    number.isTouching(gear)
                }
                assert(touchedNumbers.count() == 2)
                touchedNumbers[0].number * touchedNumbers[1].number
            }
            return gearProducts.sum()
        }

    companion object {
        fun <T> parseForPattern(
            localInput: String,
            pattern: String,
            schematicSequenceBuilder: (value: String, row: Int, start: Int) -> T
        ): List<T> {
            val schematicSequences = localInput.split("\n").mapIndexed { row, line ->
                Regex(pattern).findAll(line).map {
                    schematicSequenceBuilder(it.value, row, it.range.first)
                }
            }.flatMap { it }

            schematicSequences.forEach {
                logger.info { "Found number: $it" }
            }
            return schematicSequences
        }

        fun parseForNumbers(input: String): List<SchematicNumber> {
            return parseForPattern(input, """\d+""") { value, row, start ->
                SchematicNumber(value, row, start)
            }
        }

        fun parseForSymbols(input: String): List<SchematicSymbol> {
            return parseForPattern(input, """[^\d\.]""") { value, row, start ->
                SchematicSymbol(value, row, start)
            }
        }
    }
}

class Day03 {

    fun solution01(input: String): Int {
        val schematic = Schematic(input)
        return schematic.hitSum
    }

    fun solution02(input: String): Int {
        val schematic = Schematic(input)
        return schematic.gearSum
    }
}

fun main() {
    val input = Utils.getResource("day03.txt")
    val today = Day03()
    val givenInput = "12 red cubes, 13 green cubes, and 14 blue cubes"
    val solution01 = today.solution01(input)
    val solution02 = today.solution02(input)
    println("Solution 01: $solution01")
    println("Solution 02: $solution02")
}