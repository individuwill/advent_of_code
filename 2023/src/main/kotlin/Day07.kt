import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}


class Day07 {

    enum class HandType {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        FIVE_OF_A_KIND,
    }

    data class Hand(val cards: String, val type: HandType) : Comparable<Hand> {
        // A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, or 2
        val internalCards = cards
            .replace("A", "a")
            .replace("K", "b")
            .replace("Q", "c")
            .replace("J", "d")
            .replace("T", "e")
            .replace("9", "f")
            .replace("8", "g")
            .replace("7", "h")
            .replace("6", "i")
            .replace("5", "j")
            .replace("4", "k")
            .replace("3", "l")
            .replace("2", "m")

        override operator fun compareTo(other: Hand): Int {
            return when {
                this.type > other.type -> 1
                this.type < other.type -> -1
                else -> other.internalCards.compareTo(this.internalCards)
            }
        }
    }

    fun classifyHandString(cards: String): HandType {
        val groups = cards.groupBy { it }
        val pattern = groups.values.map { it.size }.sorted()
        //        println(groups)
        //        println(pattern)
        // 1 group is always full house
        // 2 groups can be four of a kind, or full house
        //   if four of a kind, one group has size 1, and the other size 4
        //   if full house, one group has size 2, and the other size 3
        // 3 groups can be three of a kind, or two pair
        //   if three of a kind, one group has size 3, and the others have size 1
        //   if two pair, 2 groups have size 2, and the other has size 1
        // 4 groups is always one pair
        // 5 groups, default option is always high card
        return when (pattern) {
            listOf(5) -> HandType.FIVE_OF_A_KIND
            listOf(1, 4) -> HandType.FOUR_OF_A_KIND
            listOf(2, 3) -> HandType.FULL_HOUSE
            listOf(1, 3) -> HandType.THREE_OF_A_KIND
            listOf(1, 1, 3) -> HandType.THREE_OF_A_KIND
            listOf(1, 2, 2) -> HandType.TWO_PAIR
            listOf(1, 1, 1, 2) -> HandType.ONE_PAIR
            else -> HandType.HIGH_CARD
        }
    }

    fun parseHandsAndBids(input: String): List<Pair<Hand, Long>> = input
        .trim()
        .split("\n")
        .map { it.split(" ") }
        .map { (handString, bidString) ->
            Hand(handString, classifyHandString(handString)) to bidString.toLong()
        }

    fun solution01(input: String): Long {
        return parseHandsAndBids(input).sortedBy { it.first }.mapIndexed { index, (hand, bid) ->
            (index + 1) * bid
        }.sum()
    }

    fun solution02(input: String): Int {
        return 0
    }
}

fun main() {
    val input = Utils.getResource("day07.txt")
    val today = Day07()
    val solution01 = today.solution01(input)
    val solution02 = today.solution02(input)
    println("Solution 01: $solution01")
    println("Solution 02: $solution02")
}