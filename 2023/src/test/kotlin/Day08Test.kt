import kotlin.test.*

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
        val expected = 13019L
        val actual = today.solution01(input)
        assertEquals(expected, actual)
    }

    @Test
    fun testCorrectTableLength() {
        val sampleInput = """
            LR

            11A = (11B, XXX)
            11B = (XXX, 11Z)
            11Z = (11B, XXX)
            22A = (22B, XXX)
            22B = (22C, 22C)
            22C = (22Z, 22Z)
            22Z = (22B, 22B)
            XXX = (XXX, XXX)
        """.trimIndent()
        val camelMap = today.parse(sampleInput)
        val expected = 8
        val actual = camelMap.table.size
        assertEquals(expected, actual)
    }

    @Test
    fun testSolutionSample02() {
        val sampleInput = """
            LR

            11A = (11B, XXX)
            11B = (XXX, 11Z)
            11Z = (11B, XXX)
            22A = (22B, XXX)
            22B = (22C, 22C)
            22C = (22Z, 22Z)
            22Z = (22B, 22B)
            XXX = (XXX, XXX)
        """.trimIndent()
        val expected = 6.0
        val actual = today.solution02(sampleInput)
        assertEquals(expected, actual)
    }

    @Ignore
    @Test
    fun testSolution02() {
        val expected = -1.0
        val actual = today.solution02(input)
        assertEquals(expected, actual)
    }

}