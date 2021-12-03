package day3

import java.io.File

fun main() {
    afternoon()
}

object Rater {
    var data = File("data/day3/input.txt").readLines()

    // returns a list with lesser ratings at position removed
    fun highPass(values: List<String>, column: Int): List<String> {
        if (values.first().length == column) {
            return listOf(values.sorted().last())
        }
        val columnData = values.map {
            it[column]
        }.sorted().joinToString("")
        val zeros = columnData.indexOf('1')
        val ones = columnData.length - zeros

        if (zeros == ones) {         // list does not change
            return values
        } else if (ones > zeros) {   // keep 1s
            return values.filter {
                it[column] == '1'
            }
        } else if (ones < zeros) {   // keep 0s
            return values.filter {
                it[column] == '0'
            }
        }
        else return listOf() // should not happen
    }

    // see highpass, but removes the more frequent values
    fun lowPass(values: List<String>, column: Int): List<String> {
        if (values.first().length == column) {
            return listOf(values.sorted().first())
        }
        val columnData = values.map {
            it[column]
        }.sorted().joinToString("")
        val zeros = columnData.indexOf('1')
        val ones = columnData.length - zeros

        if (zeros == ones) {
            return values
        } else if (ones < zeros) {
            return values.filter {
                it[column] == '1'
            }
        } else if (ones > zeros) {
            return values.filter {
                it[column] == '0'
            }
        }
        else return listOf() // should not happen
    }
}

fun afternoon() {
    var position = 0
    var gamma = Rater.data
    while (gamma.size > 1) {
        gamma = Rater.highPass(gamma, position)
        position++
    }
    position = 0
    var epsilon = Rater.data
    while (epsilon.size > 1) {
        epsilon = Rater.lowPass(epsilon, position)
        position++
    }
    println(gamma)
    println(epsilon)
    println(Integer.parseInt(gamma.first(), 2) * Integer.parseInt(epsilon.first(), 2))
}