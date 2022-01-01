package day23

import java.io.File

typealias Spots = Int
typealias Amphipod = Char

fun main() {
    breakfast()
}

data class Lounge(val amphipods: String) {

    fun getNextGen(): List<Pair<Amphipod, Int>> {
        val nextGen: MutableList<Pair<Amphipod, Int>> = mutableListOf()
        if (amphipods[0] in "BCD") nextGen.add(Pair(amphipods[0], 0))
        else if (amphipods[0] == 'A' && amphipods[4] in "BCD") nextGen.add(Pair('A', 0))
        else if (amphipods[4] in "BCD") nextGen.add(Pair(amphipods[4], 0))
        if (amphipods[1] in "ACD") nextGen.add(Pair(amphipods[1], 1))
        else if (amphipods[1] == 'B' && amphipods[5] in "ACD") nextGen.add(Pair('B', 1))
        else if (amphipods[5] in "ACD") nextGen.add(Pair(amphipods[5], 1))
        if (amphipods[2] in "ABD") nextGen.add(Pair(amphipods[2], 2))
        else if (amphipods[2] == 'C' && amphipods[6] in "ABD") nextGen.add(Pair('C', 2))
        else if (amphipods[6] in "ABD") nextGen.add(Pair(amphipods[6], 2))
        if (amphipods[3] in "ABC") nextGen.add(Pair(amphipods[3], 3))
        else if (amphipods[3] == 'D' && amphipods[7] in "ABC") nextGen.add(Pair('D', 3))
        else if (amphipods[7] in "ABC") nextGen.add(Pair(amphipods[7], 3))
        return nextGen
    }

    fun nextLoungeAfter(next: Pair<Amphipod, Int>): String {
        val room = next.second
        val index = if(amphipods[room] in "ABCD") room else room + 4
        val next = amphipods.mapIndexed { i, c ->
            if(index == i) '.' else c
        }.joinToString("")
        return next
    }
}

data class Hallway(val spots: Spots) {

    fun isFree(spot: Spots) = (spots and spot) == spot

    fun occupy(spot: Spots) = Hallway(spots or spot)

    fun leave(spot: Spots) = Hallway(spots - spot)

    fun isPathFree(spot: Spots, lounge: Int): Boolean {
        val path = loungeMap[Pair(lounge, spot)]
        val sum = path?.fold(0) {a, b -> a + b} ?: 1
        return spots and sum == 0
    }

    companion object {
        val s1 = 0b01000000
        val s2 = 0b00100000
        val s3 = 0b00010000
        val s4 = 0b00001000
        val s5 = 0b00000100
        val s6 = 0b00000010
        val s7 = 0b00000001
        val spotsList = listOf(s1, s2, s3, s4, s5, s6, s7)
        val loungeMap = mapOf(
            Pair(0, s1) to listOf(s1, s2),
            Pair(0, s2) to listOf(s2),
            Pair(0, s3) to listOf(s2, s3),
            Pair(0, s4) to listOf(s2, s3, s4),
            Pair(0, s5) to listOf(s2, s3, s4, s5),
            Pair(0, s6) to listOf(s2, s3, s4, s5, s6),
            Pair(0, s7) to listOf(s2, s3, s4, s5, s6, s7),
            Pair(1, s1) to listOf(s1, s2, s3),
            Pair(1, s2) to listOf(s2, s3),
            Pair(1, s3) to listOf(s3),
            Pair(1, s4) to listOf(s3, s4),
            Pair(1, s5) to listOf(s3, s4, s5),
            Pair(1, s6) to listOf(s3, s4, s5, s6),
            Pair(1, s7) to listOf(s3, s4, s5, s6, s7),
            Pair(2, s1) to listOf(s1, s2, s3, s4),
            Pair(2, s2) to listOf(s2, s3, s4),
            Pair(2, s3) to listOf(s3, s4),
            Pair(2, s4) to listOf(s4),
            Pair(2, s5) to listOf(s4, s5),
            Pair(2, s6) to listOf(s4, s5, s6),
            Pair(2, s7) to listOf(s4, s5, s6, s7),
            Pair(3, s1) to listOf(s1, s2, s3, s4, s5),
            Pair(3, s2) to listOf(s2, s3, s4, s5),
            Pair(3, s3) to listOf(s3, s4, s5),
            Pair(3, s4) to listOf(s4, s5),
            Pair(3, s5) to listOf(s5),
            Pair(3, s6) to listOf(s5, s6),
            Pair(3, s7) to listOf(s5, s6, s7))
    }
}

fun solve(hallway: Hallway, lounge: Lounge) {
    for (spot in Hallway.spotsList) {
        for (next in lounge.getNextGen()) {
            if (hallway.isPathFree(spot, next.second)) {
                val nextHallway = hallway.occupy(spot)
                val nextLounge = Lounge(lounge.nextLoungeAfter(next))
                if (hallway.isPathFree(spot, next.first - 'A')) {
                    if (nextLounge.amphipods[next.first - 'A'] == '.' &&
                            (nextLounge.amphipods[next.first - 'A' + 4] == '.' ||
                                    nextLounge.amphipods[next.first - 'A' + 4] == next.first)) {
                        println("could totally go home")
                        println(next.first)
                        println(nextLounge)
                    }
                }
                solve(nextHallway, nextLounge)
            }
        }
    }
}

fun breakfast() {
    val input = File("data/day23/sample.txt").readText().filter { it in 'A'..'D' }
    val lounge = Lounge(input)
    println(lounge)
    val next = lounge.getNextGen()
    println(next)
    val hall = Hallway(0b00001100)
    val hall2 = hall.occupy(Hallway.s7).leave(Hallway.s5)
    println(hall2.spots.toString(2))

    val h = Hallway(0b00000100)
    for (spot in Hallway.spotsList) {
        for (next in lounge.getNextGen()) {
            //println("checking for ${next.first} moving to $spot")

        }
    }

    solve(Hallway(0b00000000), lounge)

}


//package day23
//
//import java.io.File
//
//fun main() {
//    breakfast()
//}
//
//enum class Species(val sign: Char, val energy: Int) {
//
//    AMBER('A', 1),
//    BRONZE('B', 10),
//    COPPER('C', 100),
//    DESERT('D', 1000);
//
//    // lookup by char
//    companion object {
//        private val map = values().associateBy(Species::sign)
//        fun fromChar(sign: Char) = map[sign]
//    }
//}
//
//data class Amphipod(val species: Species)
//
//sealed class Move
//data class Steps(val species: Species, val steps: Int): Move()
//class Entrance: Move()
//class Deadend: Move()
//
//fun parseAmphipods(data: String): List<Amphipod> {
//    val names = data.filter { it in 'A'..'D' }
//    val amphipods = names.map { sign ->
//        Amphipod(Species.fromChar(sign)!!)
//    }
//    return amphipods
//}
//
//// in progress. I think this outlines a solution but its tedious
//fun solve(hallway: Int, rooms: String, path: List<Move>): List<Move> {
//    // hallway is a binary representation of the hallway
//    // rooms is a representation of the rooms
//    val position = rooms[9].toString().toInt()
//    val room = position % 4 // 0=a, 1=b, 2=c, 3=d
//    val myself = rooms[position]
//    val species = Species.fromChar(myself)!!
//
//    // path is a list of all steps
//    // (1) if its my first move
//    if (path.isEmpty()) {
//        val newRoom = rooms.toMutableList()
//        newRoom[position] = '.'
//        // try all possible strategies
//        for (strategy in 0..4) {
//            // pass it on to all other amphipods
//            // for amphipod in rooms first row...
//        }
//
//        // here we know the best result for this path and go home or return a dead endd
//    }
//    return path + Deadend()
//}
//
//fun breakfast() {
//    val input = File("data/day23/sample.txt").readText()
//    val amphipods = parseAmphipods(input)
//    println(amphipods)
//    val rooms = input.filter { it in 'A'..'D' }
//    val result = solve(0b00000000, rooms + "0", listOf())
//    println(result)
//}