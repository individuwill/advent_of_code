import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.math.abs

private val logger = KotlinLogging.logger {}

class Day05 {
    data class Seed(val number: Long)

    data class SeedRange(
        val destinationRangeStart: Long,
        val sourceRangeStart: Long,
        val rangeLength: Long
    ) {
        val sourceToDestinationDistance = abs(destinationRangeStart - sourceRangeStart)
        val sourceToDestinationDirection = if (destinationRangeStart >= sourceRangeStart) 1 else -1
        val sourceToDestinationShifter = sourceToDestinationDirection * sourceToDestinationDistance
        val sourceRange = sourceRangeStart..<(sourceRangeStart + rangeLength)

        // define way to check if in
        fun calculate(seedNumber: Long): Long {
            return if (seedNumber in sourceRange) {
                seedNumber + sourceToDestinationShifter
            } else {
                seedNumber
            }
        }

        fun calculate(seed: Seed): Long {
            return calculate(seed.number)
        }

        operator fun contains(seedNumber: Long): Boolean {
            return seedNumber in sourceRange
        }

        operator fun contains(seed: Seed): Boolean {
            return contains(seed.number)
        }
    }

    data class SeedMap(
        val mapName: String,
        val seedRanges: List<SeedRange>
    ) {
        fun calculate(seedNumber: Long): Long {
            val matchingRanges = seedRanges.filter { it.contains(seedNumber) }
            return if (matchingRanges.isEmpty()) {
                seedNumber
            } else {
                matchingRanges.first().calculate(seedNumber)
            }
        }

        fun calculate(seed: Seed): Long {
            return calculate(seed.number)
        }

        operator fun contains(seedNumber: Long): Boolean {
            return seedRanges.any { it.contains(seedNumber) }
        }
    }

    data class Almanac(val seeds: List<Seed>, val seedMaps: List<SeedMap>) {
        fun calculateLocation(seedNumber: Long): Long {
            var currentValue: Long = seedNumber
            seedMaps.forEach {
                currentValue = it.calculate(currentValue)
            }
            return currentValue
        }

        fun calculateLocation(seed: Seed): Long {
            return calculateLocation(seed.number)
        }

        fun findLowestLocation(): Long {
            val lowestSeed = seeds.minBy { calculateLocation(it) }
            return calculateLocation(lowestSeed)
        }

    }

    data class AlmanacParser(val input: String) {
        fun parseSeedMap(rawSeedMap: String): SeedMap {
            val seedMapLines = rawSeedMap.split("\n")
            val name = seedMapLines.first().split(" map:").first()
            logger.info { "Parsing seed map $name" }
            val ranges = seedMapLines.drop(1).map {
                val (destinationRangeStart, sourceRangeStart, rangeLength) = it.split(" ")
                SeedRange(destinationRangeStart.toLong(), sourceRangeStart.toLong(), rangeLength.toLong())
            }
            return SeedMap(name, ranges)
        }

        fun parse(): Almanac {
            val rawChunks = input.split("\n\n")
            val seeds = rawChunks.first().split("seeds: ").last().split(" ").map { Seed(it.toLong()) }
            println(seeds)
            val mapChunks = rawChunks.drop(1)
            val seedMaps = mapChunks.map(::parseSeedMap)
            println(seedMaps)
            return Almanac(seeds, seedMaps)
        }
    }

    fun solution01(input: String): Long {
        val almanac = AlmanacParser(input).parse()
        return almanac.findLowestLocation()
    }

    fun solution02(input: String): Long {
        return 0
    }
}

fun main() {
    val input = Utils.getResource("day05.txt")
    val today = Day05()
    val solution01 = today.solution01(input)
    val solution02 = today.solution02(input)
    println("Solution 01: $solution01")
    println("Solution 02: $solution02")
}