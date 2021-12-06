package day5

import java.io.File

fun main() {
    breakfast()
    //lunch()
    //dinner()
}

fun MutableMap<Point, Int>.addPoint(point: Point) {
    val count = this.getOrDefault(point, 0)
    this[point] = count + 1
}

data class Point (val x: Int, val y: Int)

fun dinner() {
    val map: MutableMap<Point, Int> = mutableMapOf()
    File("data/day5/input.txt").readLines().map {
        val x = it.split(" -> ")
        val (x1, y1) = x.first().split(",").map { it.toInt() }
        val (x2, y2) = x.last().split(",").map { it.toInt() }
        updateMap(map, Point(x1, y1), Point(x2, y2))
    }
    println(map.count { it.value > 1 })
}

// add all points between start and end
fun updateMap(map: MutableMap<Point, Int>, p1: Point, p2: Point) {

    // sort them to simplify things
    val (start, end) = listOf(p1, p2).sortedBy { it.x }

    // horizontal lines
    if (start.y == end.y) {
        (start.x..end.x).forEach {
            map.addPoint(Point(it, start.y))
        }
    }
    // vertical lines
    else if (start.x == end.x) {
        (minOf(start.y, end.y)..maxOf(start.y, end.y)).forEach {
            map.addPoint(Point(start.x, it))
        }
    }
    // going down
    else if (start.y < end.y) {
        (start.x..end.x).forEachIndexed { index, _ ->
            map.addPoint(Point(start.x + index, start.y + index))
        }
    }
    // going up
    else if (start.y > end.y) {
        (start.x..end.x).forEachIndexed { index, _ ->
            map.addPoint(Point(start.x + index, start.y - index))
        }
    }
}

// initial versions

class Block(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {

    fun inBlock(point: Pair<Int, Int>): Boolean {
        // remove obvious outsiders
        if (point.first !in minOf(x1, x2)..maxOf(x1, x2)
            || point.second !in minOf(y1, y2)..maxOf(y1, y2)) return false

        if (x1 == x2 || y1 == y2) return true // we now know its a line. should be all for part 1

        if (y2 < y1) {
            return point.second == y1 + x1 - point.first
        } else if (y2 > y1) {
            return point.second == y1 - x1 + point.first
        }
        return false
    }

    companion object {
        // ensure validated format for easier comparisons
        // always keep smaller x first then keep smaller y first
        fun blockFromInts(x1: Int, y1: Int, x2: Int, y2: Int): Block {
            if (x1 == x2) return Block(x1, minOf(y1, y2), x2, maxOf(y1, y2))
            if (x1 < x2) return Block(x1, y1, x2, y2)
            else return Block(x2, y2, x1, y1)
        }
        fun blockFromString(s: String): Block {
            val x = s.split(" -> ")
            val (x1, y1) = x.first().split(",").map { it.toInt() }
            val (x2, y2) = x.last().split(",").map { it.toInt() }
            return Block.blockFromInts(x1, y1, x2, y2)
        }
    }
}

object Field {
    val size = Pair(1000, 1000)
    var blocks: List<Block> = emptyList()

    fun countPoint(point: Pair<Int, Int>): Int {
        return blocks.map {
            it.inBlock(point)
        }.count { it }
    }

    fun printField() {
        for (y in 0 until size.first) {
            for (x in 0 until size.second) {
                val count = countPoint(Pair(x, y))
                if (count > 0) { print(count) }
                else { print(".") }
            }
            println()
        }
    }

    // calculates the number of points with at least min number of points
    fun countField(min: Int = 2): Int {

        return (0 until size.first).sumOf { x ->
            (0 until size.second).filter { y ->
                countPoint(Pair(x, y)) >= min
            }.count()
        }
    }
}

fun breakfast() {
    Field.blocks = File("data/day5/input.txt").readLines().map {
        Block.blockFromString(it)
    }.filter {
        it.x1 == it.x2 || it.y1 == it.y2
    }
    //Field.printField()
    println(Field.countField())
}

// same as breakfast but I no longer filter diagonals
fun lunch() {
    Field.blocks = File("data/day5/input.txt").readLines().map {
        Block.blockFromString(it)
    }
    //Field.printField()
    println(Field.countField())
}



