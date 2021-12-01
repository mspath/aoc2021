package day1

// for scale the more elegant solution from jetbrain's sebastian aigner
// see https://github.com/SebastianAigner/advent-of-code-2021/blob/master/src/main/kotlin/Day01.kt
// note windowed
// note the destructuring in count

import java.io.File

val input = File("data/day1/input.txt").readLines().map { it.toInt() }

fun snack() {
    println(
        input
            .windowed(2)
            .count { (a, b) -> b > a }
    )
    println(
        input
            .windowed(3) { it.sum() }
            .windowed(2)
            .count { (a, b) -> b > a }
    )
}