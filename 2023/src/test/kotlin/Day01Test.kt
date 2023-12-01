import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test {

    private val today = Day01()
    private val sampleResourceName = this::class.simpleName?.replace("Test", ".sample.txt")!!.lowercase()
    private val sampleInput = this::class.java.classLoader.getResource(sampleResourceName)!!.readText()
    private val inputResourceName = this::class.simpleName?.replace("Test", ".txt")!!.lowercase()
    private val input = this::class.java.classLoader.getResource(inputResourceName)?.readText()!!.trim()

    @Test
    fun testSolutionSample01() {
        val expected = 142
        val actual = today.solution01(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolutionSample02() {
        val expected = 1
        val actual = today.solution02(sampleInput)
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
        val expected = 1
        val actual = today.solution02(input)
        assertEquals(expected, actual)
    }

    @Test
    fun testDecodeLine() {
        val input = "a1b2c3d4e5f"
        val expected = 15
        val actual = today.decodeLine(input)
        assertEquals(expected, actual)
    }
}