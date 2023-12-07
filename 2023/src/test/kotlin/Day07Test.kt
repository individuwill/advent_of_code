import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class Day07Test {

    private val today = Day07()
    private val inputResourceName = this::class.simpleName?.replace("Test", ".txt")!!.lowercase()
    private val input = Utils.getResource(inputResourceName)
    private val sampleInput = """
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
    """.trimIndent()

    @Test
    fun testEnumComparison() {
        assertTrue { Day07.HandType.FIVE_OF_A_KIND > Day07.HandType.FOUR_OF_A_KIND }
        assertTrue { Day07.HandType.FOUR_OF_A_KIND > Day07.HandType.FULL_HOUSE }
        assertTrue { Day07.HandType.FULL_HOUSE > Day07.HandType.THREE_OF_A_KIND }
        assertTrue { Day07.HandType.THREE_OF_A_KIND > Day07.HandType.TWO_PAIR }
        assertTrue { Day07.HandType.TWO_PAIR > Day07.HandType.ONE_PAIR }
        assertTrue { Day07.HandType.ONE_PAIR > Day07.HandType.HIGH_CARD }
    }

    @Test
    fun testHandStrengthMatchingType() {
        // four of a kind: 33332 and 2AAAA -> 33332
        val hand1 = Day07.Hand("33332", Day07.HandType.FOUR_OF_A_KIND)
        val hand2 = Day07.Hand("2AAAA", Day07.HandType.FOUR_OF_A_KIND)
        assertTrue(hand1 > hand2)
        assertTrue(hand2 < hand1)
        // full house: 77888 and 77788 -> 77888
        val hand3 = Day07.Hand("77888", Day07.HandType.FULL_HOUSE)
        val hand4 = Day07.Hand("77788", Day07.HandType.FULL_HOUSE)
        assertTrue(hand3 > hand4)
        assertTrue(hand4 < hand3)

        val hand1_dupe = Day07.Hand("33332", Day07.HandType.FOUR_OF_A_KIND)
        assertEquals(hand1, hand1_dupe)
    }

    @Test
    fun testClassifyHandString() {
        assertEquals(Day07.HandType.FIVE_OF_A_KIND, today.classifyHandString("AAAAA"))
        assertEquals(Day07.HandType.FOUR_OF_A_KIND, today.classifyHandString("AA8AA"))
        assertEquals(Day07.HandType.FULL_HOUSE, today.classifyHandString("23332"))
        assertEquals(Day07.HandType.THREE_OF_A_KIND, today.classifyHandString("TTT98"))
        assertEquals(Day07.HandType.TWO_PAIR, today.classifyHandString("23432"))
        assertEquals(Day07.HandType.ONE_PAIR, today.classifyHandString("A23A4"))
        assertEquals(Day07.HandType.HIGH_CARD, today.classifyHandString("23456"))
    }

    @Test
    fun parseHandsAndBids() {
        val expected = listOf(
            Day07.Hand("32T3K", Day07.HandType.ONE_PAIR) to 765L,
            Day07.Hand("T55J5", Day07.HandType.THREE_OF_A_KIND) to 684L,
            Day07.Hand("KK677", Day07.HandType.TWO_PAIR) to 28L,
            Day07.Hand("KTJJT", Day07.HandType.TWO_PAIR) to 220L,
            Day07.Hand("QQQJA", Day07.HandType.THREE_OF_A_KIND) to 483L,
        )
        val actual = today.parseHandsAndBids(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolutionSample01() {
        val expected = 6440L
        val actual = today.solution01(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolution01() {
        val expected = 250602641L
        val actual = today.solution01(input)
        assertEquals(expected, actual)
    }


    @Test
    fun testSolutionSample02() {
        val expected = 5905L
        val actual = today.solution02(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolution02() {
        val expected = -1L
        val actual = today.solution02(input)
        assertEquals(expected, actual)
    }

}