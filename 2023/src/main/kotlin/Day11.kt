import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.math.absoluteValue

private val logger = KotlinLogging.logger {}


class Day11 {
    data class Galaxy(val number: Int, val row: Int, val column: Int)

    fun expandRows(input: String): String {
        val rows = mutableListOf<String>()
        input.split("\n").forEach { row ->
            rows.add(row)
            if (row.toSet() == setOf('.')) {
                rows.add(row)
            }
        }
        return rows.joinToString("\n")
    }

    fun expandColumns(input: String): String {
        val lines = input.split("\n").map { it.toMutableList() }
        val columns = lines[0].size
        ((columns - 1) downTo 0).forEach { index ->
            val values = lines.map { it[index] }
            if (values.toSet() == setOf('.')) {
                lines.forEach { it.add(index, '.') }
            }
        }
        return lines.joinToString("\n") { it.joinToString("") }
    }

    fun expandRowsAndColumns(input: String): String {
        return expandColumns(expandRows(input))
    }

    fun parse(input: String): List<Galaxy> {
        val galaxies = mutableListOf<Galaxy>()
        expandRowsAndColumns(input.trim()).split("\n").forEachIndexed { row, line ->
            line.forEachIndexed { column, char ->
                if (char == '#') {
                    galaxies.add(Galaxy(galaxies.size, row, column))
                }
            }
        }
        return galaxies
    }

    fun sumShortestPaths(galaxies: List<Pair<Int, Int>>): Int = galaxies.sumOf { (row, column) ->
        row + column
    }

    fun shortestPaths(galaxies: List<Galaxy>): List<Pair<Int, Int>> {
        if (galaxies.size == 1) {
            return listOf()
        }
        val galaxy = galaxies.first()
        val rest = galaxies.drop(1)
        val paths = rest.map { shortestPath(galaxy, it) }
        return paths + shortestPaths(rest)
    }

    fun shortestPath(from: Galaxy, to: Galaxy): Pair<Int, Int> {
        val rowDistance = (from.row.absoluteValue - to.row.absoluteValue).absoluteValue
        val columnDistance = (from.column.absoluteValue - to.column.absoluteValue).absoluteValue
        return rowDistance to columnDistance
    }

    fun solution01(input: String): Int {
        return parse(input).let(::shortestPaths).let(::sumShortestPaths)
    }

    fun solution02(input: String): Int {
        return 0
    }
}

fun main() {
    val input = Utils.getResource("day11.txt")
    val today = Day11()
    val solution01 = today.solution01(input)
    val solution02 = today.solution02(input)
    println("Solution 01: $solution01")
    println("Solution 02: $solution02")
}