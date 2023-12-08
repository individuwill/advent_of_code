import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}


class Day08 {

    fun <T> infiniteIterator(list: List<T>): Sequence<T> = sequence {
        while (true) {
            yieldAll(list)
        }
    }

    data class CamelMap(val instructions: Sequence<Int>, val table: Map<String, List<String>>)

    fun parseInstructions(input: String): Sequence<Int> =
        input
            .replace("L", "0")
            .replace("R", "1")
            .map(Char::digitToInt)
            .let(::infiniteIterator)

    val tableRegex = Regex("""(?<key>\w{3}) = \((?<left>\w{3}), (?<right>\w{3})\)""")
    fun parseTable(input: String): Map<String, List<String>> =
        tableRegex
            .findAll(input)
            .map { it.groups }
            .map { it["key"]!!.value to listOf(it["left"]!!.value, it["right"]!!.value) }
            .toMap()

    fun parse(input: String): CamelMap {
        val (instructionsString, tableString) = input.trim().split("\n\n")
        val instructions = parseInstructions(instructionsString)
        val table = parseTable(tableString)
        return CamelMap(instructions, table)
    }

    fun countSteps(camelMap: CamelMap): Long {
        val instructions = camelMap.instructions.iterator()
        var count = 0L
        var current = "AAA"
        while (!current.equals("ZZZ")) {
            count++
            val nextSteps = camelMap.table[current]!!
            current = nextSteps[instructions.next()]
        }
        return count
    }

    fun solution01(input: String): Long = parse(input).let(::countSteps)

    fun solution02(input: String): Int {
        return 0
    }
}

fun main() {
    val input = Utils.getResource("day08.txt")
    val today = Day08()
    val solution01 = today.solution01(input)
    val solution02 = today.solution02(input)
    println("Solution 01: $solution01")
    println("Solution 02: $solution02")
}