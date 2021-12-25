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

sealed class Move
data class Steps(val species: Species, val steps: Int): Move()
class Entrance: Move()
class Deadend: Move()

fun parseAmphipods(data: String): List<Amphipod> {
    val names = data.filter { it in 'A'..'D' }
    val amphipods = names.map { sign ->
        Amphipod(Species.fromChar(sign)!!)
    }
    return amphipods
}

// in progress. I think this outlines a solution but its tedious
// fixme refactor with hallway and rooms as classesddd
fun solve(hallway: Int, rooms: String, path: List<Move>): List<Move> {
    // hallway is a binary representation of the hallway
    // rooms is a representation of the rooms
    val position = rooms[9].toString().toInt()
    val room = position % 4 // 0=a, 1=b, 2=c, 3=d
    val myself = rooms[position]
    val species = Species.fromChar(myself)!!

    // path is a list of all steps
    // (1) if its my first move
    if (path.isEmpty()) {
        val newRoom = rooms.toMutableList()
        newRoom[position] = '.'
        // try all possible strategies
        for (strategy in 0..4) {
            // pass it on to all other amphipods
            // for amphipod in rooms first row...
        }

        // here we know the best result for this path and go home or return a dead endd
    }
    return path + Deadend()
}

fun breakfast() {
    val input = File("data/day23/sample.txt").readText()
    val amphipods = parseAmphipods(input)
    println(amphipods)
    val rooms = input.filter { it in 'A'..'D' }
    val result = solve(0b00000000, rooms + "0", listOf())
    println(result)
}