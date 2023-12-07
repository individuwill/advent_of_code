import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}


class Day06 {
    data class Race(val time: Long, val distance: Long)

    val spaceRegex = Regex("""\s+""")

    fun parseLine(input: String): List<Long> =
        input.split(":").last().trim().split(regex = spaceRegex).map(String::toLong)


    fun parse(input: String): List<Race> =
        input.trim().split("\n").map(::parseLine).let { (times, distances) ->
            times zip distances
        }.map { (time, distance) -> Race(time, distance) }

    fun distance(holdTime: Long, timeLimit: Long): Long {
        if (holdTime >= timeLimit) return 0
        val timeLeft = timeLimit - holdTime
        if (timeLeft <= 0) return 0
        val speed = holdTime
        return speed * timeLeft
    }

    fun winningCounts(race: Race): Double =
        (1..race.time).map { distance(it, race.time) }
            .fold(0.0) { acc, distance -> acc + if (distance > race.distance) 1 else 0 }

    fun score(races: List<Race>): Double =
        races.map(::winningCounts).reduce(Double::times)

    fun fixRacesToOne(races: List<Race>): Race =
        races.reduce { acc, r ->
            Race(
                "${acc.time}${r.time}".toLong(),
                "${acc.distance}${r.distance}".toLong()
            )
        }

    fun solution01(input: String): Double {
        return parse(input).let(::score)
    }

    fun solution02(input: String): Double {
        return parse(input).let(::fixRacesToOne).let(::winningCounts)
    }
}

fun main() {
    val input = Utils.getResource("day06.txt")
    val today = Day06()
    val solution01 = today.solution01(input)
    val solution02 = today.solution02(input)
    println("Solution 01: $solution01")
    println("Solution 02: $solution02")
}