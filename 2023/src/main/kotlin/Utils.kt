import io.github.cdimascio.dotenv.dotenv
import java.io.File
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.engine.cio.*
import kotlinx.coroutines.runBlocking

val dotenv = dotenv()

class Utils {
    companion object Static {
        @JvmStatic
//            private val sampleResourceName = this::class.simpleName?.replace("Test", ".sample.txt")!!.lowercase()
//            private val sampleInput = this::class.java.classLoader.getResource(sampleResourceName)!!.readText()
//            private val inputResourceName = this::class.simpleName?.replace("Test", ".txt")!!.lowercase()
//            private val input = this::class.java.classLoader.getResource(inputResourceName)?.readText()!!.trim()

        fun getInput(year: Int, day: Int): String {
            val sessionKey = dotenv["AOC_SESSION_KEY"]
            val url = "https://adventofcode.com/$year/day/$day/input"
            var data = ""
            runBlocking {
                val client = HttpClient(CIO).use { client ->
                    val response = client.request(url) {
                        cookie("session", sessionKey)
                    }
                    if (response.status.value in 200..299) {
                        println("Successful response!")
                        data = response.bodyAsText()
                    }
                }
            }
            return data
        }

        fun getResource(resourceName: String): String {
            val year = 2023
            val day = resourceName.split(".").first().split("day").last().toInt()
            val resource = this::class.java.classLoader.getResource(resourceName)
            if (resource == null) {
                val fileName = "./src/main/resources/$resourceName"
                val content = getInput(year, day)
                File(fileName).writeText(content)
                return content
            } else {
                return resource.readText().trim()
            }
        }
    }
}

fun main() {
//    val input = Utils.getInput(2023, 6)
//    println(input)
    val x = Utils.getResource("day05.txt")
    println(x)
}