import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class Day05Test {

    private val today = Day05()
    private val inputResourceName = this::class.simpleName?.replace("Test", ".txt")!!.lowercase()
    private val input = Utils.getResource(inputResourceName)

    @Test
    fun testFirstSeedRange() {
        // 50 98 2
        val range = Day05.SeedRange(50, 98, 2)
        assertEquals(50, range.calculate(98)) // in range
        assertEquals(51, range.calculate(99))// in range
        assertEquals(100, range.calculate(100)) // just higher
        assertEquals(97, range.calculate(97)) /// just lower
    }

    @Test
    fun testSeedMappingDifferentDirection() {
        // 98 50 2
        val range = Day05.SeedRange(98, 50, 2)
        assertEquals(98, range.calculate(50)) // in range
        assertEquals(99, range.calculate(51))// in range
        assertEquals(52, range.calculate(52)) // just higher
        assertEquals(49, range.calculate(49)) /// just lower
    }

    @Test
    fun testRangeContainsCheck() {
        val range = Day05.SeedRange(50, 98, 2)
        assertTrue(range.contains(98))
        assertTrue(range.contains(Day05.Seed(99)))
        assertFalse(range.contains(100))
        assertFalse(range.contains(Day05.Seed(97)))
    }

    @Test
    fun testSeedMap2Ranges() {
        val range1 = Day05.SeedRange(50, 98, 2)
        val range2 = Day05.SeedRange(13, 50, 2)
        val seedMap = Day05.SeedMap("test", listOf(range1, range2))
        assertEquals(50, seedMap.calculate(98))
        assertEquals(14, seedMap.calculate(51))
    }

    @Test
    fun testAlmanac2SeedMaps() {
        val range1 = Day05.SeedRange(50, 98, 2)
        val range2 = Day05.SeedRange(13, 50, 4)
        val seedMap1 = Day05.SeedMap("test1", listOf(range1))
        val seedMap2 = Day05.SeedMap("test2", listOf(range2))
        val almanac = Day05.Almanac(listOf(), listOf(seedMap1, seedMap2))
        assertEquals(13, almanac.calculateLocation(98))
        assertEquals(14, almanac.calculateLocation(99))
        assertEquals(13, almanac.calculateLocation(50))
        assertEquals(14, almanac.calculateLocation(51))
        assertEquals(15, almanac.calculateLocation(52))
        assertEquals(100, almanac.calculateLocation(100))
    }

    @Test
    fun testAlmanacLowestLocation() {
        val range1 = Day05.SeedRange(50, 98, 2)
        val range2 = Day05.SeedRange(13, 50, 4)
        val seedMap1 = Day05.SeedMap("test1", listOf(range1))
        val seedMap2 = Day05.SeedMap("test2", listOf(range2))
        val almanac = Day05.Almanac(listOf(Day05.Seed(98), Day05.Seed(56)), listOf(seedMap1, seedMap2))
        assertEquals(13, almanac.findLowestLocation())
    }

    @Test
    fun testSolutionSample01() {
        val expected = 35L
        val sampleInput = """
            seeds: 79 14 55 13

            seed-to-soil map:
            50 98 2
            52 50 48

            soil-to-fertilizer map:
            0 15 37
            37 52 2
            39 0 15

            fertilizer-to-water map:
            49 53 8
            0 11 42
            42 0 7
            57 7 4

            water-to-light map:
            88 18 7
            18 25 70

            light-to-temperature map:
            45 77 23
            81 45 19
            68 64 13

            temperature-to-humidity map:
            0 69 1
            1 0 69

            humidity-to-location map:
            60 56 37
            56 93 4
        """.trimIndent()
        val actual = today.solution01(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolution01() {
        val expected = 227653707L
        val actual = today.solution01(input)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolutionSample02() {
        val expected = -1L
        val sampleInput = """
        """.trimIndent()
        val actual = today.solution02(sampleInput)
        assertEquals(expected, actual)
    }

    @Test
    fun testSolution02() {
        val expected = -1L
        val actual = today.solution02(input)
        assertEquals(expected, actual)
    }

}