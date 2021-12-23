package day22

import java.io.File

fun main() {
    //breakfast()
    lunch()
}

fun String.toRange(): IntRange {
    val rest = this.substringAfter("=")
    val from = rest.substringBefore("..").toInt()
    val to = rest.substringAfter("..").toInt()
    return from..to
}

object Reactor {

    // part 1
    val points: MutableSet<Point> = mutableSetOf()

    fun turnOn(cuboid: Cuboid) {
        cuboid.x.forEach { x ->
            cuboid.y.forEach { y ->
                cuboid.z.forEach { z ->
                    points.add(Point(x, y, z))
                }
            }
        }
    }

    fun turnOff(cuboid: Cuboid) {
        cuboid.x.forEach { x ->
            cuboid.y.forEach { y ->
                cuboid.z.forEach { z ->
                    points.remove(Point(x, y, z))
                }
            }
        }
    }

    // part 2
    val cuboids: MutableSet<Cuboid> = mutableSetOf()

    fun turnOnFull(cuboid: Cuboid) {
        val current = cuboids.toList()
        cuboids.add(cuboid)
        current.forEach {
            if (it.hasIntersection(cuboid)) {
                cuboids.remove(it)
                val splits = it.split(cuboid)
                cuboids.addAll(splits.first)
            }
        }
    }

    fun turnOffFull(cuboid: Cuboid) {
        val current = cuboids.toList()
        current.forEach {
            if (it.hasIntersection(cuboid)) {
                cuboids.remove(it)
                val splits = it.split(cuboid)
                cuboids.addAll(splits.first)
            }
        }
    }
}

data class Point(val x: Int, val y: Int, val z: Int)

data class Cuboid(val x: IntRange, val y: IntRange, val z: IntRange) {

    val size: Long
        get() = (x.last - x.first + 1).toLong() * (y.last - y.first + 1) * (z.last - z.first + 1)

    fun hasIntersection(other: Cuboid): Boolean {
        val xIntersects = this.x.last >= other.x.first && other.x.last >= this.x.first
        val yIntersects = this.y.last >= other.y.first && other.y.last >= this.y.first
        val zIntersects = this.z.last >= other.z.first && other.z.last >= this.z.first
        return xIntersects && yIntersects && zIntersects
    }

    fun getIntersection(other: Cuboid): Cuboid {
        val xMinMax = maxOf(this.x.first, other.x.first)
        val xMaxMin = minOf(this.x.last, other.x.last)

        val yMinMax = maxOf(this.y.first, other.y.first)
        val yMaxMin = minOf(this.y.last, other.y.last)

        val zMinMax = maxOf(this.z.first, other.z.first)
        val zMaxMin = minOf(this.z.last, other.z.last)

        val xIntersect = xMinMax <= xMaxMin
        val yIntersect = yMinMax <= yMaxMin
        val zIntersect = zMinMax <= zMaxMin

        return Cuboid(xMinMax..xMaxMin, yMinMax..yMaxMin, zMinMax..zMaxMin)
    }

    // generate a list of smaller cuboids containing all points and the intersection
    fun split(other: Cuboid): Pair<List<Cuboid>, Cuboid> {
        assert(this.hasIntersection(other))

        val rest = this.getIntersection(other)
        val all: MutableList<Cuboid> = mutableListOf()
        val splitX = Cuboid.splitX(this, rest)
        all.addAll(splitX.first)
        val splitY = Cuboid.splitY(splitX.second, splitX.second.getIntersection(other))
        all.addAll(splitY.first)
        val splitZ = Cuboid.splitZ(splitY.second, splitY.second.getIntersection(other))
        all.addAll(splitZ.first)

        return Pair(all, rest)
    }

    companion object {
        fun splitX(first: Cuboid, second: Cuboid): Pair<List<Cuboid>, Cuboid> {
            // I assume I only get the intersection otherwise this would not work
            println(first.x)
            println(second.x)
            if (first.x == second.x) {
                return Pair(listOf(), first)
            }
            val splits: MutableList<Cuboid> = mutableListOf()
            if (first.x.first < second.x.first) {
                splits.add(Cuboid(first.x.first..second.x.first, first.y, first.z))
            }
            if (first.x.last > second.x.last) {
                splits.add(Cuboid(second.x.last..first.x.last, first.y, first.z))
            }
            val rest = Cuboid(second.x.first..second.x.last, first.y, first.z)
            return Pair(splits, rest)
        }

        fun splitY(first: Cuboid, second: Cuboid): Pair<List<Cuboid>, Cuboid> {
            println(first.y)
            println(second.y)
            if (first.y == second.y) {
                return Pair(listOf(), first)
            }
            val splits: MutableList<Cuboid> = mutableListOf()
            if (first.y.first < second.y.first) {
                splits.add(Cuboid(first.x, first.y.first..second.y.first, first.z))
            }
            if (first.y.last > second.y.last) {
                splits.add(Cuboid(first.x, second.y.last..first.y.last, first.z))
            }
            val rest = Cuboid(first.x, second.y.first..second.y.last, first.z)
            return Pair(splits, rest)
        }

        fun splitZ(first: Cuboid, second: Cuboid): Pair<List<Cuboid>, Cuboid> {
            println(first.z)
            println(second.z)
            if (first.z == second.z) {
                return Pair(listOf(), first)
            }
            val splits: MutableList<Cuboid> = mutableListOf()
            if (first.z.first < second.z.first) {
                splits.add(Cuboid(first.x, first.y, first.z.first..second.z.first))
            }
            if (first.z.last > second.z.last) {
                splits.add(Cuboid(first.x, first.y, second.z.last..first.z.last))
            }
            val rest = Cuboid(first.x, first.y, second.z.first..second.z.last)
            return Pair(splits, rest)
        }
    }
}

data class Command(val command: String) {

    val coords = command.substringAfter(" ").split(",")

    val type: String
        get() = command.substringBefore(" ")

    val cuboid: Cuboid
        get() {
            return Cuboid(coords[0].toRange(), coords[1].toRange(), coords[2].toRange())
        }

    fun forReboot(): Boolean {
        val values = listOf(cuboid.x.first, cuboid.x.last, cuboid.y.first, cuboid.y.last, cuboid.z.first, cuboid.z.last)
        return values.all { it in -50..50 }
    }
}

fun breakfast() {
    val commands = File("data/day22/input.txt").readLines().map { Command(it) }.filter { it.forReboot() }
    commands.forEach { command ->
        println("executing $command")
        println("size before ${Reactor.points.size}")
        if(command.type == "on") {
            Reactor.turnOn(command.cuboid)
        } else if(command.type == "off") {
            Reactor.turnOff(command.cuboid)
        }
        println("size after ${Reactor.points.size}")
    }
    println(Reactor.points.size)
    println(commands.size)
}

// todo almost there but not quite right yet.
fun lunch() {
    val commands = File("data/day22/sample_small.txt").readLines().map { Command(it) }
    val map: MutableSet<Cuboid> = mutableSetOf()
    commands.forEach {
        if (it.type == "on") {
            println("turning on $it")
            Reactor.turnOnFull(it.cuboid)
            println(Reactor.cuboids.sumOf { it.size })
        }
        if (it.type == "off") {
            println("turning off $it")
            Reactor.turnOffFull(it.cuboid)
            println(Reactor.cuboids.sumOf { it.size })
        }
        println(Reactor.cuboids)
    }
    val result = Reactor.cuboids.sumOf { it.size }
    println(result)
    Reactor.cuboids.forEach {
        println(it)
        println(it.size)
    }
}