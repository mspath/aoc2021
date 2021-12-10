package day9

import java.io.File

class Cell(val isPeak: Boolean = false, var basinIndex: Int = -1)

object Field {
    val rows = 100
    val cols = 100
    var field: List<Cell> = emptyList()
    val basins: MutableList<MutableSet<Int>> = mutableListOf()

    fun displayField() {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val current = row * cols + col
                val symbol = if (field[current].isPeak) '^'
                else if (field[current].basinIndex > -1) field[current].basinIndex % 10
                else '.'
                print(symbol)
            }
            println()
        }
    }
}

fun main() {
    //breakfast()
    lunch()
}

// index = cell which kicks off this iteration which greedily fills the basin
// basinIndex = index of the first cell to kick of this iteration and 'name' of the basind
// prob. should wrap this in an object
fun expandBasin(index: Int, basinIndex: Int, basin: MutableSet<Int>) {
    val neighbors = index.getIndicesOfNeighbors()
    neighbors.forEach { i ->
        val cell = Field.field[i]
        if (!cell.isPeak && cell.basinIndex == -1) {
            cell.basinIndex = basinIndex
            basin.add(i)
            expandBasin(i, basinIndex, basin)
        }
    }
}

fun lunch() {
    Field.field = File("data/day9/input.txt").readLines().joinToString("").map {
        if (it == '9') Cell(true)
        else Cell()
    }
    Field.displayField()
    val cells = Field.field
    cells.forEachIndexed { index, cell ->
        if (!cell.isPeak && cell.basinIndex == -1) {
            cell.basinIndex = index
            val basin = mutableSetOf(index)
            Field.basins.add(basin)
            expandBasin(index, index, basin)
        }
    }
    val sorted = Field.basins.map {
        it.size
    }.sorted()
    println(sorted.takeLast(3))
}

fun Int.getIndicesOfNeighbors(rows: Int = Field.rows, cols: Int = Field.cols): List<Int> {
    val n = this - cols
    val w = this -1
    val e = this + 1
    val s = this + cols

    val colLeft = setOf(w)
    val colRight = setOf(e)

    var all = setOf(n, w, e, s).filter { it in 0 until rows * cols }
    if (this % cols == 0) all = all.filterNot { it in colLeft }
    if (this % cols == cols -1) all = all.filterNot { it in colRight }

    return all
}

// part 1

fun breakfast() {
    val field = File("data/day9/input.txt").readLines().joinToString("").map { it.toString().toInt() }
    val points = field.mapIndexed { index, c ->
        val neighbors = index.getIndicesOfNeighbors().map { field[it] }
        val risk = c < neighbors.minOf { it }
        if (risk) 1 + c
        else 0
    }
    println(points.sum())
}



