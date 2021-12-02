package day2

import java.io.File

fun main() {
    breakfast()
    dinner()
}

data class Action(val command: String, val value: Int)

// parse and apply the updated interpretation of the instructions
fun dinner() {
    val inputFile = File("data/day2/input.txt")
    val content = inputFile.readLines().map {
        val (a, b) = it.split(" ")
        Action (a, b.toInt())
    }
    var depth = 0
    var distance = 0
    var aim = 0
    content.forEach {
        when (it.command) {
            "forward" -> {
                distance += it.value
                depth += aim * it.value
            }
            "down" -> aim += it.value
            "up" -> aim -= it.value
        }
    }
    println(distance * depth)
}

// parse and apply the instructions
fun breakfast() {
    val inputFile = File("data/day2/input.txt")
    val content = inputFile.readLines().map {
        val (a, b) = it.split(" ")
        Action (a, b.toInt())
    }
    var depth = 0
    var distance = 0
    content.forEach {
        when (it.command) {
            "forward" -> distance += it.value
            "down" -> depth += it.value
            "up" -> depth -= it.value
        }
    }
    println(distance * depth)
}