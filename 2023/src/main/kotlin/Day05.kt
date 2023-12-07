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
            seedRanges.forEach {
                if (seedNumber in it) {
                    return it.calculate(seedNumber)
                }
            }
            return seedNumber
        }

        fun calculate(seed: Seed): Long {
            return calculate(seed.number)
        }

        operator fun contains(seedNumber: Long): Boolean {
            return seedRanges.any { it.contains(seedNumber) }
        }
    }

    data class Almanac(val seeds: Sequence<Seed>, val seedMaps: List<SeedMap>) {
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
            val lowestSeed = seeds.minBy {
                calculateLocation(it)
            }
            return calculateLocation(lowestSeed)
        }

    }

    data class RangedAlmanac(val rangedSeeds: List<LongRange>, val seedMaps: List<SeedMap>) {
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
            var lowest = -1L
            rangedSeeds.forEach { longRange ->
                longRange.forEach { seedNumber ->
                    val calculated = calculateLocation(seedNumber)
                    if (lowest < 0) {
                        lowest = calculated
                    } else {
                        if (calculated < lowest) {
                            lowest = calculated
                        }
                    }
                }
            }
            return lowest
        }
////            logger.info { "looking for lowest location" }
//            val lowestSeed = seeds.minBy {
////                logger.debug { "calculating location for seed $it" }
//                calculateLocation(it)
//            }
//            return calculateLocation(lowestSeed)
    }


    data class AlmanacParser(val input: String, val isRanged: Boolean = false) {
        fun parseSeedMap(rawSeedMap: String): SeedMap {
            val seedMapLines = rawSeedMap.split("\n")
            val name = seedMapLines.first().split(" map:").first()
//            logger.info { "Parsing seed map $name" }
            val ranges = seedMapLines.drop(1).map {
                val (destinationRangeStart, sourceRangeStart, rangeLength) = it.split(" ")
                SeedRange(destinationRangeStart.toLong(), sourceRangeStart.toLong(), rangeLength.toLong())
            }
            return SeedMap(name, ranges)
        }

        fun parseRangedSeeds(rawSeeds: String): List<LongRange> {
            val seedNumbers = rawSeeds.split(" ").map { it.toLong() }
            val seedRanges = seedNumbers.chunked(2).map {
                val (start, rangeLength) = it
                start..<(start + rangeLength)
            }
            return seedRanges
//            seedRanges.
//            return generateSequence() {
//
//                seedRanges.flatMap { it.toList() }.map { Seed(it) }
//            }
//            return seedRanges.flatMap { it.toList() }.map { Seed(it) }.asSequence()
            /*
        logger.debug { "constructed ${seedRanges.size} seed ranges" }
        var totalCount: Double = 0.0
        seedRanges.forEach {
            val count = it.count()
            logger.debug { "size of range: ${count}" }
            totalCount += it.count()
        }
        logger.debug { "there are a total of ${totalCount} seeds" }
         */

//            return listOf<Seed>().asSequence()
//            return sequence {
//                seedRanges.forEach {
//                    it.forEach {
////                        logger.debug { "yielding $it" }
//                        yield(Seed(it))
//                    }
//                }
//            }

        }

        fun parseSeeds(rawSeeds: String): Sequence<Seed> {
            val seedNumbers = rawSeeds.split(" ").map { it.toLong() }
            return seedNumbers.map(::Seed).asSequence()
        }

        fun parse(): Almanac {
            val rawChunks = input.trim().split("\n\n")
            val rawSeeds = rawChunks.first().split("seeds: ").last()
            val seeds = parseSeeds(rawSeeds)
//            println(seeds)
            val mapChunks = rawChunks.drop(1)
            val seedMaps = mapChunks.map(::parseSeedMap)
//            println(seedMaps)
            return Almanac(seeds, seedMaps)
        }

        fun parseRanged(): RangedAlmanac {
            val rawChunks = input.trim().split("\n\n")
            val rawSeeds = rawChunks.first().split("seeds: ").last()
            val seeds = parseRangedSeeds(rawSeeds)
//            println(seeds)
            val mapChunks = rawChunks.drop(1)
            val seedMaps = mapChunks.map(::parseSeedMap)
//            println(seedMaps)
            return RangedAlmanac(seeds, seedMaps)
        }
    }

    fun solution01(input: String): Long {
        val almanac = AlmanacParser(input).parse()
        return almanac.findLowestLocation()
    }

    fun solution02(input: String): Long {
        val almanac = AlmanacParser(input, true).parseRanged()
        return almanac.findLowestLocation()
    }
}

fun main() {
    val input = Utils.getResource("day05.txt")
    val today = Day05()
//    val solution01 = today.solution01(input)
    val solution02 = today.solution02(input)
//    println("Solution 01: $solution01")
    println("Solution 02: $solution02")
}