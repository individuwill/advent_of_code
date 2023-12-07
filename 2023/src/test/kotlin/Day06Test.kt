import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class Day06Test {

    private val today = Day06()
    private val inputResourceName = this::class.simpleName?.replace("Test", ".txt")!!.lowercase()
    private val input = Utils.getResource(inputResourceName)
    private val sampleInput = """
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent()

    @Test
    fun testParseLine() {
        val expected = listOf(7L, 15, 30)
        val actual = today.parseLine("Time:      7  15   30")
        assertEquals(expected, actual)
    }

    @Test
    fun testParse() {
        val expected = listOf(
            Day06.Race(7L, 9),
            Day06.Race(15, 40),
            Day06.Race(30, 200)
        )
        val actual = today.parse(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testDistanceWithInts() {
        val timeLimit = 7L
        val testCases = listOf(
            (0L to 0L),
            (1L to 6L),
            (2L to 10L),
            (3L to 12L),
            (4L to 12L),
            (5L to 10L),
            (6L to 6L),
            (7L to 0L),
        )
        testCases.forEach { (holdTime, result) ->
            assertEquals(result, today.distance(holdTime, timeLimit))
        }
    }

    @Test
    fun testWinningCounts() {
        val races = listOf(
            Day06.Race(7L, 9L),
            Day06.Race(15L, 40L),
            Day06.Race(30L, 200L)
        )
        val testCases = races zip listOf(4.0, 8.0, 9.0)
        testCases.forEach { (race, expectedCount) ->
            assertEquals(expectedCount, today.winningCounts(race))
        }
    }

    @Test
    fun testScore() {
        val races = listOf(
            Day06.Race(7L, 9L),
            Day06.Race(15L, 40L),
            Day06.Race(30L, 200L)
        )
        val expected = 288.0
        val actual = today.score(races)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolutionSample01() {
        val expected = 288.0
        val actual = today.solution01(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolution01() {
        val expected = 6209190.0
        val actual = today.solution01(input)
        assertEquals(expected, actual)
    }


    @Test
    fun testSolutionSample02() {
        val expected = 71503.0
        val actual = today.solution02(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolution02() {
        val expected = 28545089.0
        val actual = today.solution02(input)
        assertEquals(expected, actual)
    }

}