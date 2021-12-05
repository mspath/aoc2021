package day4

import java.io.File

fun main(){
    //breakfast()
    lunch()
}

class BingoException(message:String): Exception(message)

fun generateColumns(rows: List<List<Int>>): List<List<Int>> {
    return (0..4).map { index ->
        rows.map { it ->
            it[index]
        }
    }
}

// just throwing an exception
fun winsBingo(lookup: List<List<List<Int>>>, bingo: Set<Int>): Boolean {
    val players = lookup.map { player -> player }
    players.forEach {
        println(it.first())
        val lists = it.map { list -> list }
        lists.forEach {
            val intersection = it.toSet() intersect bingo
            if (intersection.size == 5) {
                throw BingoException("${intersection}")
            }
        }
    }
    return false
}

fun breakfast() {
    val balls = File("data/day4/input_balls.txt").readText()
        .trim()
        .split(",")
        .map { it.toInt() }
    val boards = File("data/day4/input_boards.txt").readText().trim().split("\n\n")
    val lookup = boards.map {
        val rows = it.split("\n").map {
            it.trim()
                .replace("""\s+""".toRegex(), " ")
                .split(" ")
                .map { it.toInt() }
        }
        val cols = generateColumns(rows)
        rows + cols
    }
    val bingo = mutableSetOf<Int>()
    balls.forEach {
        // add ball to bingo
        bingo.add(it)
        // check if any list in lookup contains 5
        try {
            winsBingo(lookup, bingo.toSet())
        } catch (e: BingoException) {
            // just deal with first (winner) and last aka let squid win bingo
            println(e)
            println(it)
        }
    }
}

