import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.math.absoluteValue
import kotlin.math.pow

private val logger = KotlinLogging.logger {}
private val CARD_SPLIT_NUM_REGEX = Regex(" +")

data class Card(val id: Int, val winningNumbers: Set<Int>, val myNumbers: Set<Int>) {
    companion object {
        fun fromString(line: String): Card {
            val (idRaw, numbersRaw) = line.split(": ")
            val (winningNumbersRaw, myNumbersRaw) = numbersRaw.split(" | ")
            println("id: '$idRaw', winningNumbers: '$winningNumbersRaw', myNumbers: '$myNumbersRaw'")
            val winningNumbersList = winningNumbersRaw.trim().split(regex = CARD_SPLIT_NUM_REGEX).map { it.toInt() }
            val myNumbersList = myNumbersRaw.trim().split(regex = CARD_SPLIT_NUM_REGEX).map { it.toInt() }
            val winningNumbersSet = winningNumbersList.toSet()
            val myNumbersSet = myNumbersList.toSet()
            // Don't expect duplicate winning numbers, but problem does not clarify
            // if there can be duplicate numbers in my numbers
            assert(winningNumbersList.size == winningNumbersSet.size)
            assert(myNumbersList.size == myNumbersSet.size)
            return Card(
                idRaw.split(regex = CARD_SPLIT_NUM_REGEX)[1].trim().toInt(),
                winningNumbersSet,
                myNumbersSet
            )
        }
    }

    val matches: Set<Int>
        get() = myNumbers.intersect(winningNumbers)

    val score: Int
        get() {
            val pointScore = 1
            val multiplier: Double = 2.0
            val numMatches = matches.size
            return if (numMatches == 0) {
                0
            } else {
                multiplier.pow(numMatches - 1.0).toInt()
            }
        } //1 * 2 ^ (matches.size - 1)
}

class Day04 {

    fun solution01(input: String): Int {
        val cards = input.split("\n").map { Card.fromString(it) }
        return cards.sumOf { it.score }
    }

    fun solution02(input: String): Int {
        return 0
    }
}

fun main() {
    val input = Utils.getResource("day04.txt")
    val today = Day04()
    val solution01 = today.solution01(input)
    val solution02 = today.solution02(input)
    println("Solution 01: $solution01")
    println("Solution 02: $solution02")
}