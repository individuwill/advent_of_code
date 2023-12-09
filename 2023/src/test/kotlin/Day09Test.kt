import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class Day09Test {

    private val today = Day09()
    private val inputResourceName = this::class.simpleName?.replace("Test", ".txt")!!.lowercase()
    private val input = Utils.getResource(inputResourceName)
    private val sampleInput = """
        0 3 6 9 12 15
        1 3 6 10 15 21
        10 13 16 21 30 45
    """.trimIndent()

    @Test
    fun testParseToNumbers() {
        val expected = listOf(
            listOf(0.0, 3.0, 6.0, 9.0, 12.0, 15.0),
            listOf(1.0, 3.0, 6.0, 10.0, 15.0, 21.0),
            listOf(10.0, 13.0, 16.0, 21.0, 30.0, 45.0)
        )
        val actual = today.parseToNumbers(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testSubsequence() {
        val sample01 = listOf(0.0, 3.0, 6.0, 9.0, 12.0, 15.0)
        val expected01 = listOf(3.0, 3.0, 3.0, 3.0, 3.0)
        val actual01 = today.subSequence(sample01)
        assertEquals(actual01.size, sample01.size - 1)
        assertEquals(expected01, actual01)

        val sample02 = listOf(1.0, 3.0, 6.0, 10.0, 15.0, 21.0)
        val expected02 = listOf(2.0, 3.0, 4.0, 5.0, 6.0)
        val actual02 = today.subSequence(sample02)
        assertEquals(actual02.size, sample02.size - 1)
        assertEquals(expected02, actual02)

        val sample03 = listOf(10.0, 13.0, 16.0, 21.0, 30.0, 45.0)
        val expected03 = listOf(3.0, 3.0, 5.0, 9.0, 15.0)
        val actual03 = today.subSequence(sample03)
        assertEquals(actual03.size, sample03.size - 1)
        assertEquals(expected03, actual03)
    }

    @Test
    fun testSubsequences() {
        /*
        0   3   6   9  12  15
          3   3   3   3   3
            0   0   0   0
         */
        val sample01 = listOf(0.0, 3.0, 6.0, 9.0, 12.0, 15.0)
        val expected01 = listOf(
            listOf(0.0, 3.0, 6.0, 9.0, 12.0, 15.0),
            listOf(3.0, 3.0, 3.0, 3.0, 3.0),
            listOf(0.0, 0.0, 0.0, 0.0)
        )
        val actual01 = today.subSequences(sample01)
        assertEquals(expected01, actual01)

        /*
        1   3   6  10  15  21
          2   3   4   5   6
            1   1   1   1
              0   0   0
         */
        val sample02 = listOf(1.0, 3.0, 6.0, 10.0, 15.0, 21.0)
        val expected02 = listOf(
            listOf(1.0, 3.0, 6.0, 10.0, 15.0, 21.0),
            listOf(2.0, 3.0, 4.0, 5.0, 6.0),
            listOf(1.0, 1.0, 1.0, 1.0),
            listOf(0.0, 0.0, 0.0)
        )
        val actual02 = today.subSequences(sample02)
        assertEquals(expected02, actual02)

        /*
        10  13  16  21  30  45
           3   3   5   9  15
             0   2   4   6
               2   2   2
                 0   0
         */
        val sample03 = listOf(10.0, 13.0, 16.0, 21.0, 30.0, 45.0)
        val expected03 = listOf(
            listOf(10.0, 13.0, 16.0, 21.0, 30.0, 45.0),
            listOf(3.0, 3.0, 5.0, 9.0, 15.0),
            listOf(0.0, 2.0, 4.0, 6.0),
            listOf(2.0, 2.0, 2.0),
            listOf(0.0, 0.0)
        )
        val actual03 = today.subSequences(sample03)
        assertEquals(expected03, actual03)
    }

    @Test
    fun testCalculateNewValue() {
        val current = 3.0
        val bottom = 0.0
        val expected = 3.0
        val actual = today.calculateNewValue(current, bottom)
        assertEquals(expected, actual)

        assertEquals(1.0, today.calculateNewValue(1.0, 0.0))
        assertEquals(7.0, today.calculateNewValue(6.0, 1.0))
        assertEquals(23.0, today.calculateNewValue(15.0, 8.0))

        /*
        3.0   x=11.0
           8.0
         */
        assertEquals(11.0, today.calculateNewValue(3.0, 8.0))

        /*
        -5  x = -13
           -8
         */
        assertEquals(-13.0, today.calculateNewValue(-5.0, -8.0))

        /*
        -5  x = 3
           8
         */
        assertEquals(3.0, today.calculateNewValue(-5.0, 8.0))
    }

    @Test
    fun predictNext() {
        // 0   3   6   9  12  15 -> 18
        val sample01 = listOf(0.0, 3.0, 6.0, 9.0, 12.0, 15.0)
        val expected01 = 18.0
        val sequences01 = today.subSequences(sample01)
        val actual01 = today.predictNext(sequences01)
        assertEquals(expected01, actual01)

        // 1   3   6  10  15  21  -> 28
        val sample02 = listOf(1.0, 3.0, 6.0, 10.0, 15.0, 21.0)
        val expected02 = 28.0
        val sequences02 = today.subSequences(sample02)
        val actual02 = today.predictNext(sequences02)
        assertEquals(expected02, actual02)

        // 10  13  16  21  30  45 -> 68
        val sample03 = listOf(10.0, 13.0, 16.0, 21.0, 30.0, 45.0)
        val expected03 = 68.0
        val sequences03 = today.subSequences(sample03)
        val actual03 = today.predictNext(sequences03)
        assertEquals(expected03, actual03)
    }

    @Test
    fun testSolutionSample01() {
        val expected = 114.0
        val actual = today.solution01(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolution01() {
        val expected = 1834108701.0
        val actual = today.solution01(input)
        assertEquals(expected, actual)
    }


    @Test
    fun testSolutionSample02() {
        val expected = -1.0
        val actual = today.solution02(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolution02() {
        val expected = -1.0
        val actual = today.solution02(input)
        assertEquals(expected, actual)
    }

}