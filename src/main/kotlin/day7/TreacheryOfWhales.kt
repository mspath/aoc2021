package day7

import java.io.File

fun main() {
    lunch()
    //breakfast()
}

// part 2

fun fuelInc(a: Int, b: Int): Long {
    val from = minOf(a, b)
    val to = maxOf(a, b)
    val distance = to - from
    if (distance == 0) return 0L
    return distance + fuelInc(from + 1, to)
}

fun energyInc(data: List<Int>, pos: Int) = data.sumOf { fuelInc(it, pos) }

// checks every possibility and works for the provided data set
// could be refactored to stop once values become greater again or doing some binary search
fun justCheckAll(data: List<Int>): Long {
    val min = data.sorted().first()
    val max = data.sorted().last()
    return (min..max).minOf {
        energyInc(data, it)
    }
}

fun lunch() {
    val data = File("data/day7/input.txt").readLines().first().split(",")
        .map { it.toInt() }
    val result = justCheckAll(data)
    println(result)
}

// part 1

fun fuel(a: Int, b: Int) = kotlin.math.abs(a - b)

fun energy(data: List<Int>, pos: Int) = data.sumOf { fuel(it, pos) }

fun breakfast() {
    val data = File("data/day7/input.txt").readLines().first().split(",")
        .map { it.toInt() }
    val positions = data.toSet()
    val result = positions.minOf {
        energy(data, it)
    }
    println(result)
}