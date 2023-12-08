import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}


class Day08 {

    fun <T> infiniteIterator(list: List<T>): Sequence<T> = sequence {
        while (true) {
            yieldAll(list)
        }
    }

    data class CamelMap(val instructions: Sequence<Int>, val table: Map<String, List<String>>)

    class MapTicker(val camelMap: CamelMap, var current: String) {
        var count = 0.0
//        val instructions = camelMap.instructions.iterator()
//        fun tick() {
//            count++
//            val nextInstruction = instructions.next()
//            val nextSteps = camelMap.table[current]!!
//            if (count % 10000 == 0.0) {
//                println("$count: $current -> ${nextSteps[nextInstruction]} via $nextInstruction")
//            }
//            current = nextSteps[nextInstruction]
//        }

        fun tick(nextInstruction: Int) {
            count++
            val nextSteps = camelMap.table[current]!!
            current = nextSteps[nextInstruction]
        }

        override fun toString(): String {
            return "MapTicker(current=$current, count=$count)"
        }
    }

    fun parseInstructions(input: String): Sequence<Int> =
        input
            .replace("L", "0")
            .replace("R", "1")
            .map(Char::digitToInt)
            .let(::infiniteIterator)

    val tableRegex = Regex("""(?<key>\w{3}) = \((?<left>\w{3}), (?<right>\w{3})\)""")
    fun parseTable(input: String): Map<String, List<String>> {
        val table = tableRegex
            .findAll(input)
            .map { it.groups }
            .map { it["key"]!!.value to listOf(it["left"]!!.value, it["right"]!!.value) }
            .toMap().toSortedMap()
        assert(input.split("\n").size == table.size)
        val startNodes = table.keys.filter { it.endsWith("A") }
        val endNodes = table.keys.filter { it.endsWith("Z") }
        assert(startNodes.isNotEmpty())
        assert(endNodes.isNotEmpty())
        assert(startNodes.size == endNodes.size)
        return table
    }

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

    fun reachedStopCondition(mapTickers: List<MapTicker>): Boolean =
        mapTickers.all { it.current.endsWith("Z") }

    fun solution02(input: String): Double {
        val camelMap = parse(input)
        val instructions = camelMap.instructions.iterator()
        val mapTickers = camelMap.table.keys.filter { it.endsWith("A") }
            .map { MapTicker(camelMap, it) }
        mapTickers.forEach {
            println(it)
        }
        while (!reachedStopCondition(mapTickers)) {
            val nextInstruction = instructions.next()
            mapTickers.forEach { it.tick(nextInstruction) }
//            mapTickers.parallelStream().forEach { it.tick(nextInstruction) }
        }
        return mapTickers.first().count
    }
}

fun main() {
    val input = Utils.getResource("day08.txt")
    val today = Day08()
//    val solution01 = today.solution01(input)
    val solution02 = today.solution02(input)
//    println("Solution 01: $solution01")
    println("Solution 02: $solution02")
}