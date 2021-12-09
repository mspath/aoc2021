package day8

import java.io.File

fun main() {
    //breakfast()
    lunch()
    //play()
}

// helper extension to sort words by char
fun String.srt() = this.toList().sorted().joinToString("")

// did not end up using the 2 enums, but they helped to parse the problem.
enum class Segment(val digits: Set<Int>) {
    A(setOf(0, 2, 3, 5, 6, 7, 8, 9)),
    B(setOf(0, 4, 5, 6, 8, 9)),
    C(setOf(0, 1, 2, 3, 4, 7, 8, 9)),
    D(setOf(2, 3, 4, 5, 6, 8, 9)),
    E(setOf(0, 2, 6, 8)),
    F(setOf(0, 1, 3, 4, 5, 6, 7, 8, 9)),
    G(setOf(0, 2, 3, 5, 6, 8, 9))
}
enum class Digit(val segments: Set<Segment>) {
    ZERO(setOf(Segment.A, Segment.B, Segment.C, Segment.E, Segment.F, Segment.G)),
    ONE(setOf(Segment.C, Segment.F)),
    TWO(setOf(Segment.A, Segment.C, Segment.D, Segment.E, Segment.G)),
    THREE(setOf(Segment.A, Segment.C, Segment.D, Segment.F, Segment.G)),
    FOUR(setOf(Segment.B, Segment.C, Segment.D, Segment.F)),
    FIVE(setOf(Segment.A, Segment.B, Segment.D, Segment.F, Segment.G)),
    SIX(setOf(Segment.A, Segment.B, Segment.D, Segment.E, Segment.F, Segment.G)),
    SEVEN(setOf(Segment.A, Segment.C, Segment.F)),
    EIGHT(setOf(Segment.A, Segment.B, Segment.C, Segment.D, Segment.E, Segment.F, Segment.G)),
    NINE(setOf(Segment.A, Segment.B, Segment.C, Segment.D, Segment.F, Segment.G))
}

// get possible digits for the length of a word
fun getDigitsForSize(size: Int) = Digit.values().filter {
        it.segments.size == size
    }.toSet()

// part 1 just check for the length of the digits...

fun breakfast() {
    val lines = File("data/day8/input.txt").readLines()
    val result = lines.sumOf {
        it.split(" | ").last().split(" ").filter {
            it.length in listOf(2, 3, 4, 7)
        }.size
    }
    println(result)
}

// part 2

fun solve(words: List<String>, digits: List<String>): Int {
    val dictionary = words.toMutableList()
     // pick out the numbers we know
    val n1 = dictionary.filter { it.length == 2 }.first()
    val n4 = dictionary.filter { it.length == 4 }.first()
    val n7 = dictionary.filter { it.length == 3 }.first()
    val n8 = dictionary.filter { it.length == 7 }.first()
    // 3 left sized 6: 0, 6, 9
    // 3 left sized 5: 2, 3, 5
    val n6 = dictionary.filter { it.length == 6 && !(n1[0] in it && n1[1] in it) }.first()
    dictionary.remove(n6)
    val n9 = dictionary.filter { it.length == 6 && n4[0] in it && n4[1] in it && n4[2] in it && n4[3] in it }.first()
    dictionary.remove(n9)
    val n0 = dictionary.filter { it.length == 6 }.first()
    dictionary.remove(n0)
    val n3 = dictionary.filter { it.length == 5 && n1[0] in it && n1[1] in it }.first()
    dictionary.remove(n3)
    val n5 = dictionary.filter {
        (it.toSet() intersect n6.toSet()).size == 5
    }.first()
    dictionary.remove(n5)
    val n2 = dictionary.first()
    val map = mapOf(n0.srt() to 0, n1.srt() to 1, n2.srt() to 2, n3.srt() to 3, n4.srt() to 4,
        n5.srt() to 5, n6.srt() to 6, n7.srt() to 7, n8.srt() to 8, n9.srt() to 9)
    val result = digits.map {
        map.getOrDefault(it.srt(), 0)
    }.joinToString("").toInt()
    return result
}

fun lunch() {
    val lines = File("data/day8/input.txt").readLines()
    println(lines.sumOf {
        val (a, b) = it.split(" | ")
        val words = a.split(" ")
        val digits = b.split(" ")
        solve(words, digits)
    })
}