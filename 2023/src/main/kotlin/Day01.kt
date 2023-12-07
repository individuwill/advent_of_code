class Day01 {

    fun replaceAllNumbers(line: String): String {
        return line.replace("one", "1")
            .replace("two", "2")
            .replace("three", "3")
            .replace("four", "4")
            .replace("five", "5")
            .replace("six", "6")
            .replace("seven", "7")
            .replace("eight", "8")
            .replace("nine", "9")
    }

    // "eight3one"
    fun firstNumber(line: String): Int {
        val leadingString = line.takeWhile { !it.isDigit() }
        println("leadingString: $leadingString")
        if (leadingString.isEmpty()) {
            return line.first().digitToInt()
        }
        val firstDigit = if (leadingString.length == line.length) null else line[leadingString.length].digitToInt()
        println("firstDigit: $firstDigit")
        val leadingDigits = leadingString
            .fold("") { acc, c ->
                replaceAllNumbers(acc + c)
            }
            .filter { it.isDigit() }
            .map { it.digitToInt() }
        return if (leadingDigits.isNotEmpty()) {
            leadingDigits.first()
        } else {
            firstDigit!!
        }
    }

    fun lastNumber(line: String): Int {
        val lastDigitIndex = line.lastIndexOfAny("123456789".toCharArray())
        val lastDigit = if (lastDigitIndex == -1) null else line[lastDigitIndex].digitToInt()
        val trailingString = line.takeLast(line.length - lastDigitIndex - 1)
        println("trailingString: $trailingString")
        val numberizedTrailingString = trailingString.reversed().fold("") { acc, c ->
            replaceAllNumbers(c + acc)
        }
        println("numberizedTrailingString: $numberizedTrailingString")
        val trailingDigits = numberizedTrailingString
            .filter { it.isDigit() }
            .map { it.digitToInt() }
        return if (trailingDigits.isNotEmpty()) {
            trailingDigits.last()
        } else {
            lastDigit!!
        }
    }

    fun numberize(line: String): String {
        val numberized = line.fold("") { acc, c ->
            // println("acc: $acc, c: $c")
            (acc + c).replace("one", "1")
                .replace("two", "2")
                .replace("three", "3")
                .replace("four", "4")
                .replace("five", "5")
                .replace("six", "6")
                .replace("seven", "7")
                .replace("eight", "8")
                .replace("nine", "9")
        }
        val finalNumberized = numberized.replace("one", "1")
            .replace("two", "2")
            .replace("three", "3")
            .replace("four", "4")
            .replace("five", "5")
            .replace("six", "6")
            .replace("seven", "7")
            .replace("eight", "8")
            .replace("nine", "9")

        if (finalNumberized != line) {
            println("line: $line")
            println("numberized: $numberized")
            println("finalNumberized: $finalNumberized")
        }
        return finalNumberized
    }

    fun decodeLine(line: String): Int {
        val justNumbers = line.filter(Char::isDigit)
        val desiredNumber = justNumbers.first().toString() + justNumbers.last().toString()
        return Integer.parseInt(desiredNumber)
    }

    fun solution01(input: String): Int {
        val lines = input.trim().split("\n")
        val numbers = lines.map(this::decodeLine)
        val total = numbers.sum()
        return total
    }

    fun solution02(input: String): Int {
        val lines = input.split("\n")
        val numbers = lines.map {
            val firstNumber = firstNumber(it)
            val lastNumber = lastNumber(it)
            val combined = "$firstNumber$lastNumber"
            Integer.parseInt(combined)
        }
        val total = numbers.sum()
        return total
    }
}

fun main() {
    println("Hello, World!")
}