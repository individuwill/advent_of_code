import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}


class Day06 {
    data class Race(val time: Int, val distance: Int)

    val spaceRegex = Regex("""\s+""")

    fun parseLine(input: String): List<Int> =
        input.split(":").last().trim().split(regex = spaceRegex).map(String::toInt)


    fun parse(input: String): List<Race> =
        input.split("\n").map(::parseLine).let { (times, distances) ->
            times zip distances
        }.map { (time, distance) -> Race(time, distance) }

    fun distance(holdTime: Int, timeLimit: Int): Int {
        if (holdTime >= timeLimit) return 0
        val timeLeft = timeLimit - holdTime
        if (timeLeft <= 0) return 0
        val speed = holdTime
        return speed * timeLeft
    }

    fun winningCounts(race: Race): Int =
        (1..race.time).map { distance(it, race.time) }.filter { it > race.distance }.count()

    fun score(races: List<Race>): Int =
        races.map(::winningCounts).reduce(Int::times)


    fun solution01(input: String): Int {
        return parse(input).let(::score)
    }

    fun solution02(input: String): Int {
        return 0
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