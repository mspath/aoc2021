package day13

import java.io.File

fun main() {
    //breakfast()
    lunch()
}

data class Point(var x: Int, var y: Int)

data class FoldInstruction(val axis: Char, var value: Int)

fun foldX(points: List<Point>, fold: Int): List<Point> {
    val result = mutableSetOf<Point>()
    val partLeft = points.filter { it.x <= fold }
    val partRight = points.filter { it.x > fold }.map {
        Point(it.x - 2 * (it.x - fold), it.y)
    }
    result += partLeft
    result += partRight
    return result.toList()
}

fun foldY(points: List<Point>, fold: Int): List<Point> {
    val result = mutableSetOf<Point>()
    val partTop = points.filter { it.y <= fold }
    val partBottom = points.filter { it.y > fold }.map {
        Point(it.x,it.y  - 2 * (it.y - fold))
    }
    result += partTop
    result += partBottom
    return result.toList()
}

fun fold(points: List<Point>, instruction: FoldInstruction): List<Point> {
    when(instruction.axis) {
        'x' -> return foldX(points, instruction.value)
        'y' -> return foldY(points, instruction.value)
        else -> return points
    }
}

fun breakfast() {
    val points = File("data/day13/input_points.txt").readLines().map {
        val (x, y) = it.split(",").map { it.toInt() }
        Point(x, y)
    }
    val instruction = File("data/day13/input_instructions.txt").readLines().map {
        val next = it.substringAfter("fold along ")
        val axis = next[0]
        val value = next.substringAfter("=").toInt()
        FoldInstruction(axis, value)
    }.first()
    // we just need the first fold in part 1
    val result = foldX(points, instruction.value)
    println(result.size)
}

fun printPoints(points: List<Point>) {
    val xmax = points.maxOf { it.x }
    val ymax = points.maxOf { it.y }

    for (y in 0..ymax) {
        for (x in 0..xmax) {
            val p = Point(x, y)
            if (points.contains(p)) print('#')
            else print(' ')
        }
        println()
    }
}

fun lunch() {
    val points = File("data/day13/input_points.txt").readLines().map {
        val (x, y) = it.split(",").map { it.toInt() }
        Point(x, y)
    }
    val instructions = File("data/day13/input_instructions.txt").readLines().map {
        val next = it.substringAfter("fold along ")
        val axis = next[0]
        val value = next.substringAfter("=").toInt()
        FoldInstruction(axis, value)
    }
    var result = points.map { it }
    instructions.forEach {
        result = fold(result, it)
    }
    printPoints(result)
}