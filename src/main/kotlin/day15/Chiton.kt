package day15

import java.io.File

fun main() {
    breakfast()
    //lunch()
}

fun breakfast() {
    (0..Cave.size * Cave.size).forEach {
        val cell = Cave.getBestNext()
        cell?.run {
            val ns = cell.getNeighbors()
            ns.forEach {
                it.acceptVisit(cell)
            }
            cell.jobDone = true
        }
    }
    Cave.data.last().let {
        println(it)
    }
}

data class Cell(val index: Int, val risk: Int, var cost: Int = Int.MAX_VALUE) {

    var jobDone = false

    fun getNeighbors(): List<Cell> {
        val neighbors = index.getIndicesOfNeighbors().map {
            Cave.data[it]
        }
        return neighbors
    }

    fun acceptVisit(visitor: Cell) {
        if (visitor.cost + risk < cost) {
            cost = visitor.cost + risk
        }
    }
}

object Cave {
    // fixme hardcoded
    val size = 500
    val data = File("data/day15/inputFive.txt").readLines()
        .joinToString("")
        .mapIndexed { index, c ->
            if (index == 0) Cell(index, c.toString().toInt(), 0)
            else Cell(index, c.toString().toInt())
        }

    // fixme use some sort of priority queue but it completed as is
    fun getBestNext(): Cell? {
        return data.filterNot { it.jobDone }
            .sortedBy {
            it.cost
        }.firstOrNull()
    }
}

fun Int.getIndicesOfNeighbors(): List<Int> {
    val rows = Cave.size
    val cols = Cave.size
    val n = this - cols
    val w = this - 1
    val e = this + 1
    val s = this + cols
    var all = setOf(n, w, e, s).filter { it in 0 until rows * cols }
    if (this % cols == 0) all = all.filterNot { it in setOf(w) }
    if (this % cols == cols - 1) all = all.filterNot { it in setOf(e) }
    return all
}

fun shift(c: Char): Char {
    return when (c) {
        '1' -> '2'
        '2' -> '3'
        '3' -> '4'
        '4' -> '5'
        '5' -> '6'
        '6' -> '7'
        '7' -> '8'
        '8' -> '9'
        '9' -> '1'
        else -> '\n'
    }
}

fun lunch() {
    // implemented by generating an updated input file. it gets the job done
    val input = File("data/day15/input.txt")
    val output = File("data/day15/inputFive.txt")
    val data = input.readLines()
    val row0 = data.map { col0 ->
        val col1 = col0.map { c -> shift(c) }.joinToString("")
        val col2 = col1.map { c -> shift(c) }.joinToString("")
        val col3 = col2.map { c -> shift(c) }.joinToString("")
        val col4 = col3.map { c -> shift(c) }.joinToString("")
        col0 + col1 + col2 + col3 + col4
    }.joinToString("\n")
    val row1 = row0.map { shift(it) }.joinToString("")
    val row2 = row1.map { shift(it) }.joinToString("")
    val row3 = row2.map { shift(it) }.joinToString("")
    val row4 = row3.map { shift(it) }.joinToString("")
    output.writeText(row0 + "\n")
    output.appendText(row1 + "\n")
    output.appendText(row2 + "\n")
    output.appendText(row3 + "\n")
    output.appendText(row4)
}