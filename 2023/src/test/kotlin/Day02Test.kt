import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class Day02Test {

    private val today = Day02()
    private val inputResourceName = this::class.simpleName?.replace("Test", ".txt")!!.lowercase()
    private val input = Utils.getResource(inputResourceName)

    @Test
    fun testSolutionSample01() {
        val expected = 8
        val sampleInput = """
            Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
            Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
            Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
            Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
        """.trimIndent()
        val givenInput = """
            12 red cubes, 13 green cubes, 14 blue cubes
        """.trimIndent()
        val actual = today.solution01(sampleInput, givenInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testParseGivenRound() {
        val expected = Round(12, 13, 14)
        val givenInput = """
            12 red, 13 green, 14 blue
        """.trimIndent()
        val actual = Round.fromText(givenInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testParseSingleGame() {
        val input = "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red"
        val expected = Game(4, listOf(
                Round(3, 1, 6),
                Round(6, 3, 0),
                Round(14, 3, 15)
        ))
        val actual = Game.fromText(input)
        assertEquals(expected, actual)
    }

    @Test
    fun parseMatch() {
        val input = """
            Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        """.trimIndent()
        val expected = Match(listOf(
                Game(1, listOf(
                        Round(4, 0, 3),
                        Round(1, 2, 6),
                        Round(0, 2, 0)
                )),
                Game(2, listOf(
                        Round(0, 2, 1),
                        Round(1, 3, 4),
                        Round(0, 1, 1)
                ))
        ))
        val actual = Match.fromText(input)
        assertEquals(expected, actual)
    }

    @Test
    fun testFittingRound() {
        val round = Round(4, 1, 3)
        val givenRound = Round(12, 13, 14)
        assertTrue(round.isPossibleGiven(givenRound))
    }

    @Test
    fun testNotFittingRound() {
        val round = Round(4, 1, 3)
        val givenRound = Round(3, 13, 14)
        assertFalse(round.isPossibleGiven(givenRound))
    }

    @Test
    fun testScoreWithNoFittingGiven() {
        val match = Match(listOf(
                Game(1, listOf(
                        Round(4, 0, 3),
                        Round(1, 2, 6),
                        Round(0, 2, 0)
                )),
                Game(2, listOf(
                        Round(0, 2, 1),
                        Round(1, 3, 4),
                        Round(0, 1, 1)
                ))
        ))
        val givenRound = Round(0, 0, 0)
        val expected = 0
        val actual = match.scoreWith(givenRound)
        assertEquals(expected, actual)
    }

    @Test
    fun testScoreWithFirstFittingGiven() {
        val match = Match(listOf(
                Game(1, listOf(
                        Round(4, 0, 3),
                        Round(1, 2, 6),
                        Round(0, 2, 0)
                )),
                Game(2, listOf(
                        Round(0, 2, 1),
                        Round(1, 13, 4),
                        Round(0, 1, 1)
                ))
        ))
        val givenRound = Round(10, 10, 10)
        val expected = 1
        val actual = match.scoreWith(givenRound)
        assertEquals(expected, actual)
    }

    @Test
    fun testScoreWithSecondFittingGiven() {
        val match = Match(listOf(
                Game(1, listOf(
                        Round(4, 0, 3),
                        Round(1, 12, 6),
                        Round(0, 2, 0)
                )),
                Game(2, listOf(
                        Round(0, 2, 1),
                        Round(1, 3, 4),
                        Round(0, 1, 1)
                ))
        ))
        val givenRound = Round(10, 10, 10)
        val expected = 2
        val actual = match.scoreWith(givenRound)
        assertEquals(expected, actual)
    }

    @Test
    fun testScoreWithAllFittingGiven() {
        val match = Match(listOf(
                Game(1, listOf(
                        Round(4, 0, 3),
                        Round(1, 2, 6),
                        Round(0, 2, 0)
                )),
                Game(2, listOf(
                        Round(0, 2, 1),
                        Round(1, 3, 4),
                        Round(0, 1, 1)
                ))
        ))
        val givenRound = Round(10, 10, 10)
        val expected = 3
        val actual = match.scoreWith(givenRound)
        assertEquals(expected, actual)
    }

    //
//    @Test
//    fun testSolutionSample02() {
//        val expected = 281
//        val sampleInput = """
//        """.trimIndent()
//        val actual = today.solution02(sampleInput)
//        assertEquals(expected, actual)
//    }
//
    @Test
    fun testSolution01() {
        val expected = 2545
        val givenInput = """
            12 red cubes, 13 green cubes, and 14 blue cubes
        """.trimIndent()
        val actual = today.solution01(input, givenInput)
        assertEquals(expected, actual)
    }
//
//    @Test
//    fun testSolution02() {
//        val expected = 55686
//        val actual = today.solution02(input)
//        print("actual: $actual")
//        assertTrue(actual > 55680)
//        assertEquals(expected, actual)
//    }

}