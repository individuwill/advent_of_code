data class Round(val red: Int, val green: Int, val blue: Int) {
    companion object {
        private val CUBE_REGEX = Regex("""(?<count>\d+) (?<color>red|blue|green)""")
        fun fromText(input: String): Round {
            println("Constructing round from: `$input`")
            val matches = CUBE_REGEX.findAll(input).toList()
            println(matches)
            assert(matches.size <= 3)
            val pairs = matches.map { Pair(it.groups["color"]!!.value, it.groups["count"]?.value!!.toInt()) }
            println(pairs)
            val pairsDict = pairs.toMap()
            println(pairsDict)
            return Round(
                    red = pairsDict.getOrDefault("red", 0),
                    green = pairsDict.getOrDefault("green", 0),
                    blue = pairsDict.getOrDefault("blue", 0)
            )
        }
    }

    fun isPossibleGiven(round: Round): Boolean {
        return red <= round.red && green <= round.green && blue <= round.blue
    }

    val power: Int
        get() = red * green * blue
}

data class Game(val id: Int, val rounds: List<Round>) {
    companion object {
        fun fromText(input: String): Game {
            println("Constructing game from: `$input`")
            val id = input.split(":")[0].split(" ")[1].toInt()
            println("id: $id")
            val rounds = input.split(":")[1].split(";").map { Round.fromText(it) }
            return Game(id, rounds)
        }
    }

    fun isPossibleGiven(round: Round): Boolean {
        return rounds.all { it.isPossibleGiven(round) }
    }

    val minimalRound: Round
        get() {
            val red = rounds.maxOf { it.red }
            val green = rounds.maxOf { it.green }
            val blue = rounds.maxOf { it.blue }
            return Round(red = red, green = green, blue = blue)
        }
}

data class Match(val games: List<Game>) {
    companion object {
        fun fromText(input: String): Match {
            val games = input.split("\n").map { Game.fromText(it) }
            return Match(games)
        }
    }

    fun scoreWith(round: Round): Int {
        val fittingGames = games.filter { it.isPossibleGiven(round) }
        if (fittingGames.isNotEmpty()) {
            return fittingGames.sumOf { it.id }
        }
        return 0
    }

    val power: Int
        get() = games.sumOf {
            it.minimalRound.power
        }
}

class Day02 {

    fun solution01(matchInput: String, roundInput: String): Int {
        val match = Match.fromText(matchInput)
        val round = Round.fromText(roundInput)
        return match.scoreWith(round)
    }

    fun solution02(matchInput: String): Int {
        val match = Match.fromText(matchInput)
        return match.power
    }
}

fun main() {
    val input = Utils.getResource("day02.txt")
    val today = Day02()
    val givenInput = "12 red cubes, 13 green cubes, and 14 blue cubes"
    val solution01 = today.solution01(input, givenInput)
    val solution02 = today.solution02(input)
    println("Solution 01: ${solution01}")
    println("Solution 02: ${solution02}")
}