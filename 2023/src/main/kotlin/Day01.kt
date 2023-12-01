class Day01 {
    fun decodeLine(line: String): Int {
        val justNumbers = line.filter(Char::isDigit)
        val desiredNumber = justNumbers.first().toString() + justNumbers.last().toString()
        return Integer.parseInt(desiredNumber)
    }

    fun solution01(input: String): Int {
        val lines = input.split("\n")
        val numbers = lines.map(this::decodeLine)
        val total = numbers.sum()
        return total
    }

    fun solution02(input: String): Int {
        return 0
    }
}

fun main() {
    println("Hello, World!")
}