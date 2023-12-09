import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.math.absoluteValue

private val logger = KotlinLogging.logger {}


class Day09 {

    fun parseToNumbers(input: String): List<List<Double>> {
        return input.trim().split("\n").map { line ->
            line.split(" ").map(String::toDouble)
        }
    }

    fun subSequence(input: List<Double>): List<Double> =
        input.windowed(2).map { (first, second) -> second - first }

    fun subSequences(input: List<Double>): List<List<Double>> {
        if (input.all { it == 0.0 }) {
            return listOf(input)
        }
        return listOf(input) + subSequences(subSequence(input))
    }

    fun calculateNewValue(current: Double, bottom: Double): Double {
        return current + bottom
    }

    fun calculateNewValue(currentSequence: List<Double>, bottom: Double): Double {
        val last = currentSequence.last()
        val newValue = calculateNewValue(last, bottom)
        return newValue
    }

    fun predictNext(input: List<List<Double>>): Double {
        if (input.isEmpty()) {
            return 0.0
        }
        return calculateNewValue(input.first(), predictNext(input.drop(1)))
    }

    fun solution01(input: String): Double {
        return parseToNumbers(input).sumOf { history ->
            history.let(::subSequences).let(::predictNext)
        }
    }

    fun solution02(input: String): Double {
        return 0.0
    }
}

fun main() {
    val input = Utils.getResource("day09.txt")
    val today = Day09()
    val solution01 = today.solution01(input)
    val solution02 = today.solution02(input)
    println("Solution 01: $solution01")
    println("Solution 02: $solution02")
}