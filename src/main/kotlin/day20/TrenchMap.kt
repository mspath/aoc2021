package day20

import java.io.File

data class Pixel(val x: Int, val y: Int)

object TrenchMap {

    var algo: String = ""
    var pixels: Set<Pixel> = emptySet()

    // fixme currently invert is tightly coupled and assumes bit 0 to be on and bit 511 to be off (flipping)
    var invert = false

    fun printMap() {
        for (y in getMinY()..getMaxY()) {
            for (x in getMinX()..getMaxX()) {
                if (pixels.contains(Pixel(x, y))) print('#') else print('.')
            }
            println()
        }
    }

    fun getMinX () = pixels.minByOrNull { it.x }?.x ?: Int.MIN_VALUE

    fun getMaxX () = pixels.maxByOrNull { it.x }?.x ?: Int.MAX_VALUE

    fun getMinY () = pixels.minByOrNull { it.y }?.y ?: Int.MIN_VALUE

    fun getMaxY () = pixels.maxByOrNull { it.y }?.y ?: Int.MAX_VALUE

    fun codeForPoint(x: Int, y: Int): Int {
        val neighbors = listOf(Pixel(x - 1, y - 1), Pixel(x, y - 1), Pixel(x + 1, y - 1),
            Pixel(x - 1, y), Pixel(x, y), Pixel(x + 1, y),
            Pixel(x - 1, y + 1), Pixel(x, y + 1), Pixel(x + 1, y + 1))
        val code = neighbors.map {
            if (!invert) if (pixels.contains(it)) '1' else '0'
            else if (pixels.contains(it)) '0' else '1'
        }.joinToString("").toInt(2)
        return code
    }

    fun transformPixels() {
        if (invert) invertPixels()
        val pixels: MutableSet<Pixel> = mutableSetOf()
        for (y in getMinY() - 2..getMaxY() + 2) {
            for (x in getMinX() - 2..getMaxX() + 2) {
                val code = codeForPoint(x, y)
                if (algo[code] == '#') pixels.add(Pixel(x, y))
            }
        }
        this.pixels = pixels.toSet()
        invert = false
    }

    fun invertPixels() {
        val inverted: MutableSet<Pixel> = mutableSetOf()
        for (y in getMinY()..getMaxY()) {
            for (x in getMinX()..getMaxX()) {
                if (!pixels.contains(Pixel(x, y))) inverted.add(Pixel(x, y))
            }
        }
        pixels =  inverted.toSet()
    }
}

fun main() {
    breakfast()
}

fun breakfast() {
    val algo = File("data/day20/input_algorithm.txt").readText().trim()
    val data = File("data/day20/input_image.txt").readLines()
    val pixels = data.mapIndexed { y, s ->
        s.mapIndexed { x, c ->
            if (c == '#') Pixel(x, y) else null
        }.filterNotNull()
    }.flatten().toMutableSet()
    TrenchMap.algo = algo
    TrenchMap.pixels = pixels
    repeat(25) {
        TrenchMap.transformPixels()
        TrenchMap.invert = true
        TrenchMap.transformPixels()
    }
    TrenchMap.printMap()
    println(TrenchMap.pixels.size)
}