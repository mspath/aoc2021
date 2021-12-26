package day25

import java.io.File

fun main() {
    breakfast()
}

data class Position(val x: Int, val y: Int)

class Floor(val floor: List<String>) {

    val height: Int
        get() = floor.size

    val width: Int
        get() = floor.first().length

    fun print() {
        floor.forEach { row ->
            println(row)
        }
    }

    private fun nextRowEast(row: String): String {
        val next = row.mapIndexed { index, c ->
            when(index) {
                0 -> {
                    if (c == '.' && row.last() == '>') '>'
                    else if (c == '>' && row[1] == '.') '.'
                    else c
                }
                row.length - 1 -> {
                    if (c == '>' && row[0] == '.') '.'
                    else if (c == '.' && row[index - 1] == '>') '>'
                    else c
                }
                else -> {
                    if (c == '>' && row[index + 1] == '.') '.'
                    else if (c == '.' && row[index - 1] == '>') '>'
                    else c
                }
            }
        }.joinToString("")
        if (next != row) changed = true
        return next
    }

    fun nextGenerationEast() = Floor(floor.map { row -> nextRowEast(row) })

    private fun nextRowSouth(text: String, row: Int): String {
        assert(row < height)
        val next = text.mapIndexed { index, c ->
            when(row) {
                // carry over the previous from height - 1
                0 -> {
                    if (c == '.' && floor[height - 1][index] == 'v') 'v'
                    else if (c == 'v' && floor[1][index] == '.') '.'
                    else c
                }
                // last row depends on row 0
                height - 1 -> {
                    if (c == 'v' && floor[0][index] == '.') '.'
                    else if (c == '.' && floor[row - 1][index] == 'v') 'v'
                    else c
                }
                else -> {
                    if (c == 'v' && floor[row + 1][index] == '.') '.'
                    else if (c == '.' && floor[row - 1][index] == 'v') 'v'
                    else c
                }
            }
        }.joinToString("")
        if (next != next) changed = true
        return next
    }

    fun nextGenerationSouth() = Floor(floor.mapIndexed { row, text -> nextRowSouth(text, row) })
}

var changed = true

fun breakfast() {
    val floor = Floor(File("data/day25/input.txt").readLines())
    var next = floor
    var counter = 0
    while (changed) {
        counter++
        changed = false
        next = next.nextGenerationEast()
        next = next.nextGenerationSouth()
    }
    next.print()
    println(counter)
}