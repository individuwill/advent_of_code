import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day01Test {

    private val today = Day01()
    private val sampleInput = """
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet
    """.trimIndent()
    private val inputResourceName = this::class.simpleName?.replace("Test", ".txt")!!.lowercase()
    private val input = Utils.getResource(inputResourceName)

    @Test
    fun testSolutionSample01() {
        val expected = 142
        val actual = today.solution01(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolutionSample02() {
        val expected = 281
        val newSampleInput = """
            two1nine
            eightwothree
            abcone2threexyz
            xtwone3four
            4nineeightseven2
            zoneight234
            7pqrstsixteen
        """.trimIndent()
        val actual = today.solution02(newSampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolution01() {
        val expected = 55029
        val actual = today.solution01(input)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolution02() {
        val expected = 55686
        val actual = today.solution02(input)
        print("actual: $actual")
        assertTrue(actual > 55680)
        assertEquals(expected, actual)
    }

    @Test
    fun testDecodeSimpleLine() {
        val input = "a1b2c3d4e5f"
        val expected = 15
        val actual = today.decodeLine(input)
        assertEquals(expected, actual)
    }

    @Test
    fun testNumberize() {
        val input = "4nineeightseven2"
        val expected = "49872"
        val actual = today.numberize(input)
        assertEquals(expected, actual)
    }

    @Test
    fun testOverlapping() {
        val input = "eightwothree"
        val expected = "8wo3"
        val actual = today.numberize(input)
        assertEquals(expected, actual)
    }

    @Test
    fun firstNumber() {
        val input = "two1eighthree"
        val expected = 2
        val actual = today.firstNumber(input)
        assertEquals(expected, actual)
    }

    @Test
    fun firstNumberAllWords() {
        val input = "eightwothree"
        val expected = 8
        val actual = today.firstNumber(input)
        assertEquals(expected, actual)
    }

    @Test
    fun lastNumber() {
        val input = "two1eighthree"
        val expected = 3
        val actual = today.lastNumber(input)
        assertEquals(expected, actual)
    }

    @Test
    fun lastNumberAllWords() {
        val input = "eightwothree"
        val expected = 3
        val actual = today.lastNumber(input)
        assertEquals(expected, actual)
    }
}