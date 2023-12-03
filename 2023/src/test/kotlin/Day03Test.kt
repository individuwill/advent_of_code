import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class Day03Test {

    private val today = Day03()
    private val inputResourceName = this::class.simpleName?.replace("Test", ".txt")!!.lowercase()
    private val input = Utils.getResource(inputResourceName)

    @Test
    fun testParseInputNumbers() {
        val sampleInput = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...${'$'}.*....
            .664.598..
        """.trimIndent()
        val numbers = Schematic.parseForNumbers(sampleInput)
        assertEquals(10, numbers.count())
    }

    @Test
    fun testParseInputSymbols() {
        val sampleInput = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...${'$'}.*....
            .664.598..
        """.trimIndent()
        val numbers = Schematic.parseForSymbols(sampleInput)
        assertEquals(6, numbers.count())
    }

    @Test
    fun testSymbolBorders() {
        val sampleInput = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...${'$'}.*....
            .664.598..
        """.trimIndent()
        val schematic = Schematic(sampleInput)
        println(schematic.symbolBorders)
        assertTrue(false)
    }

    @Test
    fun testHitNumbers() {
        val sampleInput = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...${'$'}.*....
            .664.598..
        """.trimIndent()
        val schematic = Schematic(sampleInput)
        println(schematic.hitNumbers.map { it.number })
        schematic.symbols.forEach {
            println("$it border: ${it.borderCoordinates}")
        }
        assertEquals(8, schematic.hitNumbers.count())
        // 664
        // 592
        // 58 -- correct
        // 35 position(2,2,3)=good closest symbol position(1,3,3)=good
        // 114 -- correct
    }

    @Test
    fun testSolutionSample01() {
        val expected = 4361
        val sampleInput = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...${'$'}.*....
            .664.598..
        """.trimIndent()
        val actual = today.solution01(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolution01() {
        val expected = 522726
        val actual = today.solution01(input)
        assertEquals(expected, actual)
    }

    @Test
    fun testTouchedNumbers() {
        val sampleInput = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...${'$'}.*....
            .664.598..
        """.trimIndent()
        val schematic = Schematic(sampleInput)
        assertEquals(schematic.hitNumbers.count(), schematic.touchedNumbers.count())
        assertEquals(schematic.hitNumbers, schematic.touchedNumbers)
    }

    @Test
    fun testGears() {
        val sampleInput = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...${'$'}.*....
            .664.598..
        """.trimIndent()
        val schematic = Schematic(sampleInput)
        assertEquals(2, schematic.gears.count())
    }

    @Test
    fun testSolutionSample02() {
        val expected = 467835
        val sampleInput = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...${'$'}.*....
            .664.598..
        """.trimIndent()
        val actual = today.solution02(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolution02() {
        val expected = 81721933
        val actual = today.solution02(input)
        assertEquals(expected, actual)
    }

}