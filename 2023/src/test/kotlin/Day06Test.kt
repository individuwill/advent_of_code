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
        val expected = listOf(7, 15, 30)
        val actual = today.parseLine("Time:      7  15   30")
        assertEquals(expected, actual)
    }

    @Test
    fun testParse() {
        val expected = listOf(
            Day06.Race(7, 9),
            Day06.Race(15, 40),
            Day06.Race(30, 200)
        )
        val actual = today.parse(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testDistanceWithInts() {
        val timeLimit = 7
        val testCases = listOf(
            (0 to 0),
            (1 to 6),
            (2 to 10),
            (3 to 12),
            (4 to 12),
            (5 to 10),
            (6 to 6),
            (7 to 0),
        )
        testCases.forEach { (holdTime, result) ->
            assertEquals(result, today.distance(holdTime, timeLimit))
        }
    }

    @Test
    fun testWinningCounts() {
        val races = listOf(
            Day06.Race(7, 9),
            Day06.Race(15, 40),
            Day06.Race(30, 200)
        )
        val testCases = races zip listOf(4, 8, 9)
        testCases.forEach { (race, expectedCount) ->
            assertEquals(expectedCount, today.winningCounts(race))
        }
    }

    @Test
    fun testScore() {
        val races = listOf(
            Day06.Race(7, 9),
            Day06.Race(15, 40),
            Day06.Race(30, 200)
        )
        val expected = 288
        val actual = today.score(races)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolutionSample01() {
        val expected = 288
        val actual = today.solution01(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolution01() {
        val expected = 6209190
        val actual = today.solution01(input)
        assertEquals(expected, actual)
    }


    @Test
    fun testSolutionSample02() {
        val expected = -1
        val actual = today.solution02(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolution02() {
        val expected = -1
        val actual = today.solution02(input)
        assertEquals(expected, actual)
    }

}