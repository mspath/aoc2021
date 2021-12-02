package day2

import java.io.File

fun main() {
    breakfast()
    brunch()
    dinner()
    lunch()
}

data class Action(val command: String, val value: Int) {

    val transform: (Position) -> Unit = {
        when (command) {
            "forward" -> {
                it.distance += value
                it.depth += it.aim * value
            }
            "down" -> it.aim += value
            "up" -> it.aim -= value
        }
    }

    val transformSimple: (Position) -> Unit = {
        when (command) {
            "forward" -> it.distance += value
            "down" -> it.depth += value
            "up" -> it.depth -= value
        }
    }
}

data class Position(var distance: Int = 0, var depth: Int = 0, var aim: Int = 0)

// refactoring brunch using fold
fun lunch() {
    val inputFile = File("data/day2/input.txt")
    val content = inputFile.readLines().map {
        val (a, b) = it.split(" ")
        Action (a, b.toInt())
    }
    val position = content.fold(Position()) { position, action ->
        run { action.transform(position) }
        position
    }
    println(position.depth * position.distance)
}

// refactoring breakfast using fold
fun dinner() {
    val inputFile = File("data/day2/input.txt")
    val content = inputFile.readLines().map {
        val (a, b) = it.split(" ")
        Action (a, b.toInt())
    }
    val position = content.fold(Position()) { position, action ->
        run { action.transformSimple(position) }
        position
    }
    println(position.depth * position.distance)
}

// parse and apply the updated interpretation of the instructions
fun brunch() {
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