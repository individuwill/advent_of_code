import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class Day11Test {

    private val today = Day11()
    private val inputResourceName = this::class.simpleName?.replace("Test", ".txt")!!.lowercase()
    private val input = Utils.getResource(inputResourceName)
    private val sampleInput = """
        ...#......
        .......#..
        #.........
        ..........
        ......#...
        .#........
        .........#
        ..........
        .......#..
        #...#.....
    """.trimIndent()

    @Test
    fun testExpandRows() {
        val expected = """
            ...#......
            .......#..
            #.........
            ..........
            ..........
            ......#...
            .#........
            .........#
            ..........
            ..........
            .......#..
            #...#.....
        """.trimIndent()
        val actual = today.expandRows(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testExpandColumns() {
        val expected = """
            ....#........
            .........#...
            #............
            .............
            ........#....
            .#...........
            ............#
            .............
            .........#...
            #....#.......
        """.trimIndent()
        val actual = today.expandColumns(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testExpandRowsAndColumns() {
        val expected = """
            ....#........
            .........#...
            #............
            .............
            .............
            ........#....
            .#...........
            ............#
            .............
            .............
            .........#...
            #....#.......
        """.trimIndent()
        val actual = today.expandRowsAndColumns(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testParse() {
        val actual = today.parse(sampleInput)
        actual.forEach {
            println(it)
        }
        assertEquals(9, actual.size)
        assertEquals(0, actual[0].number)
        assertEquals(8, actual[8].number)
    }

    @Test
    fun testShortestPath() {
        val g0 = Day11.Galaxy(number = 0, row = 0, column = 4)
        val g8 = Day11.Galaxy(number = 8, row = 11, column = 5)
        var (row, column) = today.shortestPath(g0, g8)
        assertEquals(11, row)
        assertEquals(1, column)
        assertEquals(11 to 1, today.shortestPath(g8, g0))
    }

    @Test
    fun testShortestPathCounts() {
        val paths = today.shortestPaths(today.parse(sampleInput))
        assertEquals(36, paths.size)
    }


    @Test
    fun testSolutionSample01() {
        val expected = 374
        val actual = today.solution01(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolution01() {
        val expected = 10490062
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