package day11

import java.io.File
import java.util.*

fun Int.getIndicesOfNeighbors(rows: Int = 10, cols: Int = 10): List<Int> {
    val n = this - cols
    val nw = n - 1
    val ne = n + 1
    val w = this -1
    val e = this + 1
    val s = this + cols
    val sw = s - 1
    val se = s + 1
    val colLeft = setOf(w, nw, sw)
    val colRight = setOf(e, ne, se)
    var all = setOf(n, w, e, s, nw, ne, sw, se).filter { it in 0 until rows * cols }
    if (this % cols == 0) all = all.filterNot { it in colLeft }
    if (this % cols == cols -1) all = all.filterNot { it in colRight }
    return all
}

data class Octopus(val id: Int, var energy: Int, var hasFlashed: Boolean = false, var flashCount: Int = 0) {

    fun increaseForDay() {
        energy++
    }

    fun resetForNight() {
        if (hasFlashed) {
            hasFlashed = false
            energy = 0
        }
    }

    fun flash(list: MutableList<Octopus>)  {
        if(hasFlashed) {
            list.remove(this)
            return
        }
        val neighbors = id.getIndicesOfNeighbors().map { Swarm.data[it] }.filterNot { it.hasFlashed }
        neighbors.forEach {
            it.energy++
            if (it.energy > 9) list.add(it)
        }
        energy = 0
        flashCount++
        hasFlashed = true
        list.remove(this)
    }

    fun checkFlash(list: MutableList<Octopus>) {
        if (energy > 9) {
            list.add(this)
        }
    }
}

fun main() {
    breakfast()
    lunch()
}

fun printGrid(list: List<Octopus>) {
    for (row in 0 until 10) {
        for (col in 0 until 10) {
            val current = row * 10 + col
            print(list[current].energy)
        }
        println()
    }
    println("----------")
}

fun nextDay(list: List<Octopus>) {

    // energy for the day
    for (octopus in list) {
        octopus.increaseForDay()
    }

    // fill the first wave flashlist
    val flashList: MutableList<Octopus> = mutableListOf()
    for (octopus in list) {
        octopus.checkFlash(flashList)
    }

    while (flashList.size > 0) {
        val next = flashList.first()
        next.flash(flashList)
    }

    // just to make sure octopuses which flashed get reset properly
    for (octopus in list) {
        octopus.resetForNight()
    }
}

object Swarm {
    val data = File("data/day11/input.txt").readLines()
        .joinToString("")
        .mapIndexed { index, c ->
            Octopus(index, c.toString().toInt())
        }

    fun inSync() = data.map { it.energy }.toSet().size == 1
}

// part 1
fun breakfast() {
    (0 until 100).forEach {
        nextDay(Swarm.data)
    }
    println(Swarm.data.sumOf { it.flashCount })
}

// part 2
fun lunch() {
    var day = 0
    // just keep iterating until the swarm comis in sync
    while (true) {
        day++
        nextDay(Swarm.data)
        if (Swarm.inSync()) break
    }
    println(day)
}