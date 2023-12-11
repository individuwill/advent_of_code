import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class Day10Test {

    private val today = Day10()
    private val inputResourceName = this::class.simpleName?.replace("Test", ".txt")!!.lowercase()
    private val input = Utils.getResource(inputResourceName)
    private val sampleInput = """
        ..F7.
        .FJ|.
        SJ.L7
        |F--J
        LJ...
    """.trimIndent()
    private val sampleParsed = listOf(
        mutableListOf(
            Day10.Token.GROUND,
            Day10.Token.GROUND,
            Day10.Token.SOUTH_EAST,
            Day10.Token.SOUTH_WEST,
            Day10.Token.GROUND,
        ),
        mutableListOf(
            Day10.Token.GROUND,
            Day10.Token.SOUTH_EAST,
            Day10.Token.NORTH_WEST,
            Day10.Token.VERTICAL,
            Day10.Token.GROUND,
        ),
        mutableListOf(
            Day10.Token.START,
            Day10.Token.NORTH_WEST,
            Day10.Token.GROUND,
            Day10.Token.NORTH_EAST,
            Day10.Token.SOUTH_WEST,
        ),
        mutableListOf(
            Day10.Token.VERTICAL,
            Day10.Token.SOUTH_EAST,
            Day10.Token.HORIZONTAL,
            Day10.Token.HORIZONTAL,
            Day10.Token.NORTH_WEST,
        ),
        mutableListOf(
            Day10.Token.NORTH_EAST,
            Day10.Token.NORTH_WEST,
            Day10.Token.GROUND,
            Day10.Token.GROUND,
            Day10.Token.GROUND,
        ),
    )

    @Test
    fun printInputAsDrawing() {
        val actual = today.renderAsDrawing(input)
        println(actual)
    }

    @Test
    fun testParse() {
        /*
        [GROUND, GROUND, SOUTH_EAST, SOUTH_WEST, GROUND]
        [GROUND, SOUTH_EAST, NORTH_WEST, VERTICAL, GROUND]
        [START, NORTH_WEST, GROUND, NORTH_EAST, SOUTH_WEST]
        [VERTICAL, SOUTH_EAST, HORIZONTAL, HORIZONTAL, NORTH_WEST]
        [NORTH_EAST, NORTH_WEST, GROUND, GROUND, GROUND]
         */
        val expected = sampleParsed
        val actual = today.parse(sampleInput)
        actual.forEach {
            println(it)
        }
        assertEquals(expected, actual)
    }

    @Test
    fun testMoveToStart() {
        val expected = 2 to 0
        val actual = Day10.Walker(sampleParsed).moveToStart()
        assertEquals(expected, actual)
    }

    @Test
    fun testSolutionSample01() {
        val expected = 8.0
        val actual = today.solution01(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolution01() {
        val expected = -1.0
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