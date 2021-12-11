package day10

import java.io.File
import java.util.*

fun main() {
    //breakfast()
    lunch()
}

val lettersOpen = listOf('(', '[', '{', '<')
val lettersClose = listOf(')', ']', '}', '>')
val mapClose = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
val mapValue = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
val mapValueComplete = mapOf(')' to 1L, ']' to 2L, '}' to 3L, '>' to 4L)

// return value is the score according to the rules
fun checkCorrupt(line: String): Int {
    val stack = Stack<String>()
    for (c in line) {
        if (c in lettersOpen) {
            stack.push(lettersOpen.joinToString("") + mapClose[c])
        } else {
            if (c in stack.peek()) {
                stack.pop()
            } else {
                return mapValue.getOrDefault(c, 0)
            }
        }
    }
    return 0
}

fun breakfast() {
    val data = File("data/day10/input.txt").readLines()
    val result = data.map {
        checkCorrupt(it)
    }.sum()
    println(result)
}

// calculates the score according to the rules
fun calculateAutocomplete(line: String): Long {
    return line.fold(0) { total, next ->
        total * 5 + mapValueComplete.getOrDefault(next, 0L)
    }
}

fun checkIncomplete(line: String): Long {
    val stack = Stack<String>()
    for (c in line) {
        if (c in lettersOpen) {
            stack.push(lettersOpen.joinToString("") + mapClose[c])
        } else if (c in stack.peek()) {
            stack.pop()
        }
    }
    val result = stack.map {
        it.filter { it !in lettersOpen }
    }.joinToString("").reversed()
    return calculateAutocomplete(result)
}

fun lunch() {
    val data = File("data/day10/input.txt").readLines()
    val result = data.map {
        if (checkCorrupt(it) == 0) checkIncomplete(it)
        else 0
    }.filter { it > 0 }.sorted()
    val middle = (result.size) / 2
    println(result[middle])
}