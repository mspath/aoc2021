package day1

import java.io.File

fun main() {
    lunch()
    dinner()
    snack()
}

// refactored afternoon using a map
fun dinner() {
    val inputFile = File("data/day1/input.txt")
    val numbers = inputFile.readLines().map { it.toInt() }
    val increments = numbers.mapIndexed { index, i ->
        if (index < 3) {
            false
        } else {
            i > numbers[index - 3]
        }
    }.count { it }
    println("number of increments: $increments")
}

// refactored morning using a map
fun lunch() {
    val inputFile = File("data/day1/input.txt")
    val numbers = inputFile.readLines().map { it.toInt() }
    val increments = numbers.mapIndexed { index, i ->
        if (index == 0) {
            false
        } else {
            i > numbers[index - 1]
        }
    }.count { it }
    println("number of increments: $increments")
}

// count the number of increments of measurements
fun morning() {
    val inputFile = File("data/day1/input.txt")
    val numbers = inputFile.readLines().map { it.toInt() }
    var increments = 0
    var previous = numbers[0]
    for (i in 1 until numbers.size) {
        if (numbers[i] > previous) {
            increments++
        }
        previous = numbers[i]
    }
    println("number of increments: $increments")
}

// count the number of increments of measurements within a 3-day window
fun afternoon() {
    val inputFile = File("data/day1/input.txt")
    val numbers = inputFile.readLines().map { it.toInt() }
    var yesteryesterday = numbers[0]
    var yesterday = numbers[1]
    var today = numbers[2]
    var increments = 0
    for (i in 3 until numbers.size) {
        // don't need to add today and yesterday on both sides
        if (numbers[i] > yesteryesterday) {
            increments++
        }
        yesteryesterday = yesterday
        yesterday = today
        today = numbers[i]
    }
    println("number of increments: $increments")
}

