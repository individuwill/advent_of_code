import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class Day08Test {

    private val today = Day08()
    private val inputResourceName = this::class.simpleName?.replace("Test", ".txt")!!.lowercase()
    private val input = Utils.getResource(inputResourceName)
    private val sampleInput = """
        LLR

        AAA = (BBB, BBB)
        BBB = (AAA, ZZZ)
        ZZZ = (ZZZ, ZZZ)
    """.trimIndent()

    @Test
    fun testInfiniteIterator() {
        val instructions = today.infiniteIterator(listOf(1, 2, 3))
        val expected = listOf(1, 2, 3, 1, 2, 3, 1, 2, 3)
        val actual = instructions.take(9).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun testParseInstructions() {
        val instructions = today.parseInstructions("LRRRLRRLLRRL")
        val expected = listOf(0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0)
        val actual = instructions.take(expected.size).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun testParseTable() {
        val s = """
            AAA = (BBB, BBB)
            BBB = (AAA, ZZZ)
            ZZZ = (ZZZ, ZZZ)
        """.trimIndent()
        val table = today.parseTable(s)
        val expected = mapOf(
            "AAA" to listOf("BBB", "BBB"),
            "BBB" to listOf("AAA", "ZZZ"),
            "ZZZ" to listOf("ZZZ", "ZZZ"),
        )
        assertEquals(expected, table)
    }

    @Test
    fun testParseCamelMap() {
        val camelMap = today.parse(sampleInput)
        val expectedInstructions = listOf(0, 0, 1, 0, 0, 1)
        assertEquals(expectedInstructions, camelMap.instructions.take(expectedInstructions.size).toList())
        val expectedTable = mapOf(
            "AAA" to listOf("BBB", "BBB"),
            "BBB" to listOf("AAA", "ZZZ"),
            "ZZZ" to listOf("ZZZ", "ZZZ"),
        )
        assertEquals(expectedTable, camelMap.table)
    }


    @Test
    fun testSolutionSample01() {
        val expected = 6L
        val actual = today.solution01(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolution01() {
        val expected = 13019
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