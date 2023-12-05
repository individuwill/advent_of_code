import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day04Test {

    private val today = Day04()
    private val inputResourceName = this::class.simpleName?.replace("Test", ".txt")!!.lowercase()
    private val input = Utils.getResource(inputResourceName)

    @Test
    fun testCardScoreNoMatches() {
        val card = Card(1, setOf(1, 2, 3, 4, 5), setOf(10, 11, 12, 13, 14))
        val expected = 0
        assertEquals(expected, card.score)
    }

    @Test
    fun testCardScoreSingleMatches() {
        val card = Card(1, setOf(1, 2, 3, 4, 5), setOf(10, 11, 12, 13, 5))
        val expected = 1
        assertEquals(expected, card.score)
    }

    @Test
    fun testCardScoreAllMatches() {
        val card = Card(1, setOf(1, 2, 3, 4, 5), setOf(1, 2, 3, 4, 5))
        val expected = 16
        assertEquals(expected, card.score)
    }

    @Test
    fun testCardScoreSomeMatches() {
        val card = Card(1, setOf(1, 5, 10, 16, 2), setOf(1, 2, 3, 4, 0))
        val expected = 2
        assertEquals(expected, card.score)
    }

    @Test
    fun testCardScoreDuplicates() {
        val card = Card(1, setOf(1, 2, 3, 4, 5), setOf(5, 5, 6, 7, 8))
        val expected = 1
        assertEquals(expected, card.score)
    }

    @Test
    fun testMatchingNumbers() {
        val card = Card(1, setOf(1, 5, 10, 16, 2), setOf(1, 2, 3, 4, 5))
        val expected = setOf(1, 5, 2)
        assertEquals(expected, card.matches)
    }

    @Test
    fun testParseCard() {
        val expected = Card(1, setOf(1, 5, 10, 16, 2), setOf(1, 2, 3, 4, 5))
        val actual = Card.fromString("Card 1: 1 5 10 16 2 | 1 2 3 4 5")
        assertEquals(expected, actual)
    }

    @Test
    fun testSolutionSample01() {
        val expected = 13
        val sampleInput = """
            Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
            Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
            Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
            Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
            Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
            Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
        """.trimIndent()
        val actual = today.solution01(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolution01() {
        val expected = 32609
        val actual = today.solution01(input)
        assertEquals(expected, actual)
    }


    @Test
    fun testCardManager() {
        val sampleInput = """
            Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
            Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
            Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
            Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
            Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
            Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
        """.trimIndent()
        val originalCards = today.parseCards(sampleInput)
        val cardManager = CardManager(originalCards)
        val expected = 30
        val actual = cardManager.countEmUp()
        assertEquals(expected, actual)
    }


    @Test
    fun testSolution02() {
        val expected = 14624680
        val actual = today.solution02(input)
        assertEquals(expected, actual)
    }

}