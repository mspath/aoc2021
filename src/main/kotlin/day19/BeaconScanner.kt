package day19

import java.io.File

class BeaconConfusionException(message:String): Exception(message)

fun main() {
    try {
        breakfast()
    } catch (bce: BeaconConfusionException) {
        println(bce)
        println("don't worry, matrix math and backtracking is hard.")
    }
}

// the possible orientation for the scanners
enum class Orientation {
    NORTH_0,
    EAST_0,
    SOUTH_0,
    WEST_0,
    UP_0,
    DOWN_0,
    NORTH_90,
    EAST_90,
    SOUTH_90,
    WEST_90,
    UP_90,
    DOWN_90,
    NORTH_180,
    EAST_180,
    SOUTH_180,
    WEST_180,
    UP_180,
    DOWN_180,
    NORTH_270,
    EAST_270,
    SOUTH_270,
    WEST_270,
    UP_270,
    DOWN_270
}

// the 3d coordinate system of beacons and scanners
data class Ocean(val beacons: MutableList<Beacon>, val scanners: List<Scanner>)

// fixed
data class Beacon(val x: Int, var y: Int, val z: Int)

fun Observation.turn(): Observation {
    return Observation(y, x * -1, z)
}

fun Observation.roll(): Observation {
    return Observation(z * -1, y, x)
}

fun Observation.spin(): Observation {
    return Observation(x, z, y * -1)
}

// relative to each scanner
data class Observation(val x: Int, val y: Int, val z: Int)

data class Scanner(val id: Int, var x: Int?, var y: Int?, var z: Int?, val observations: List<Observation>) {
    var orientation: Orientation? = null
}

fun initOcean(): Ocean {
    val regex = """\w?--- scanner (\d+) ---\n""".toRegex()
    val input = File("data/day19/sample.txt").readText().split(regex)
    val scanners = input.filter { it.isNotEmpty() }.mapIndexed { index, s ->
        val observations = s.trim().split("\n").map {
            val (x, y, z) = it.split(",").map { it.toInt()}
            Observation(x, y, z)
        }
        if (index == 0) {
            val scanner = Scanner(index, 0, 0, 0, observations)
            scanner.orientation = Orientation.NORTH_0
            scanner
        } else {
            Scanner(index, null, null, null, observations)
        }
    }
    val beacons = scanners[0].observations.map {
        Beacon(it.x, it.y, it.z)
    }.toMutableList()
    val ocean = Ocean(beacons, scanners)
    return ocean
}

fun breakfast() {
    val ocean = initOcean()
    println(ocean.scanners.filter { it.orientation != null })
    ocean.scanners.forEach {
        println(it)
    }
    throw BeaconConfusionException("too hard. revisit later")
}