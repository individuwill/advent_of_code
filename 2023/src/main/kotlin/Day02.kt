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
}

class Day02 {

    fun solution01(matchInput: String, roundInput: String): Int {
        val match = Match.fromText(matchInput)
        val round = Round.fromText(roundInput)
        return match.scoreWith(round)
    }

    fun solution02(matchInput: String, roundInput: String): Int {
        val match = Match.fromText(matchInput)
        val round = Round.fromText(roundInput)
        return match.scoreWith(round)
    }
}

//fun main() {
//    val input = Utils.getResource("day02.txt")
//    val today = Day02()
//    val solution01 = today.solution01(input)
//    val solution02 = today.solution02(input)
//    println("Solution 01: ${solution01}")
//    println("Solution 02: ${solution02}")
//}