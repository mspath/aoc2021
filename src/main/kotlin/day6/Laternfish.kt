package day6

import java.io.File

fun main() {
    //breakfast()
    //lunch()
    snack()
}

// part 2 version 2 simplified

fun nextDay(fish: Map<Int, Long>) = mapOf(0 to fish.getOrDefault(1, 0L),
    1 to fish.getOrDefault(2, 0L),
    2 to fish.getOrDefault(3, 0L),
    3 to fish.getOrDefault(4, 0L),
    4 to fish.getOrDefault(5, 0L),
    5 to fish.getOrDefault(6, 0L),
    6 to fish.getOrDefault(7, 0L) + fish.getOrDefault(0, 0L),
    7 to fish.getOrDefault(8, 0L),
    8 to fish.getOrDefault(0, 0L))

fun snack() {
    var fish = File("data/day6/input.txt").readLines()
        .first().split(",")
        .map { it.toInt() }.groupingBy { it }.eachCount()
        .map { it.key to it.value.toLong() }.toMap()
    for (i in 1..256) {
        fish = nextDay(fish)
    }
    println(fish.map { it.value }.sum())
}

// part 2 version 1 recursive

fun offspring(initial: Int, days: Int): Long {
    if (days <= initial) return 0L
    else return 1L + offspring(initial, days - 7) + offspring(8, days - initial - 1)
}

fun lunch() {
    val fish = File("data/day6/input.txt").readLines()
        .first().split(",").map { it.toInt() }.toMutableList()
    val result = fish.map {
        offspring(it, 256) + 1
    }.sum()
    println(result)
}

// part 1

fun iterate(fish: MutableList<Int>) {
    for (next in fish.indices) {
        val days = fish[next]
        if (days == 0) {
            fish[next] = 6
            fish.add(8)
            continue
        }
        fish[next]--
    }
}

fun breakfast() {
    val fish = File("data/day6/input.txt").readLines()
        .first().split(",").map { it.toInt() }.toMutableList()
    for (day in 0 until 80) {
        iterate(fish)
    }
    println(fish.size)
}



