package day17

data class Point(var x: Int, var y: Int) {
    // fun hitSample() = x in 20..30 && y in -10..-5
    fun hit() = x in areaXmin..areaXmax && y in areaYmin..areaYmax

    companion object {
        val areaXmin = 206
        val areaXmax = 250
        val areaYmin = -105
        val areaYmax = -57
    }
}

data class Probe(val velocityX: Int, val velocityY: Int) {
    var vX = velocityX
    var vY = velocityY
    var point = Point(0, 0)

    fun nextPoint(): Point {
        point.x += vX
        point.y += vY
        vY -= 1
        if (vX > 0) vX -= 1
        else if (vX < 0) vX += 1
        return point
    }

    // fixme add a little bit of cleverness
    fun hitsArea(): Boolean {
        val p = Probe(velocityX, velocityY)
        return (1..500).any {
            val pt = p.nextPoint()
            pt.hit()
        }
    }

    fun highestPoint(): Int {
        if (velocityY < 2) return velocityY
        val highestPoint = velocityY.toDouble() / 2 * (velocityY + 1)
        return highestPoint.toInt()
    }
}

fun main() {
    // today we don't need to parse the input
    // val sample = "target area: x=20..30, y=-10..-5"
    // val input = "target area: x=206..250, y=-105..-57"
    breakfast()
}

fun breakfast() {
    var maxHeight = Int.MIN_VALUE
    val counter: MutableSet<Probe> = mutableSetOf()
    // fixme add a bit of cleverness to the heuristics of making probes
    for (x in 1..Point.areaXmax) {
        for (y in Point.areaYmin..200) {
            val probe = Probe(x, y)
            if (probe.hitsArea()) {
                val high = probe.highestPoint()
                if (high > maxHeight) { maxHeight = high }
                counter.add(probe)
            }
        }
    }
    println("trick shot height: $maxHeight")
    println("possible probes: ${counter.size}")
}