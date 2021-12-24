package day23

import java.io.File

fun main() {
    breakfast()
}

enum class Species(val sign: Char, val energy: Int) {

    AMBER('A', 1),
    BRONZE('B', 10),
    COPPER('C', 100),
    DESERT('D', 1000);

    // lookup by char
    companion object {
        private val map = values().associateBy(Species::sign)
        fun fromChar(sign: Char) = map[sign]
    }
}

data class Amphipod(val species: Species)

fun parseAmphipods(data: String): List<Amphipod> {
    val names = data.filter { it in 'A'..'D' }
    val amphipods = names.map { sign ->
        Amphipod(Species.fromChar(sign)!!)
    }
    return amphipods
}

// todo try again. I went down various unfruitful paths and need to find the right abstraction
fun breakfast() {
    val input = File("data/day23/sample.txt").readText()
    val amphipods = parseAmphipods(input)
    println(amphipods)
}