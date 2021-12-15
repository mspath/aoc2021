package day14

import java.io.File
import java.util.*

fun main() {
    breakfast("CKFFSCFSCBCKBPBCSPKP")
    lunch("CKFFSCFSCBCKBPBCSPKP", 10)
}

data class Polymer(val name: String, val right: Boolean = false, val depth: Int = 0) {

    fun getRight(): Polymer {
        if (right) {
            if (Polymer.hasInsert(name)) {
                val insert = template.get(name)!!
                return Polymer(name[0].toString() + insert, true, depth + 1)
            } else {
                return Polymer(name, true, depth + 1)
            }
        } else {
            if (Polymer.hasInsert(name)) {
                val insert = template.get(name)!!
                return Polymer(name[0].toString() + insert, false, depth + 1)
            } else {
                return Polymer(name, false, depth + 1)
            }
        }
    }

    fun getLeft(): Polymer {
        if (Polymer.hasInsert(name)) {
            val insert = template.get(name)!!
            return Polymer(insert + name[1].toString(), false, depth + 1)
        } else {
            return Polymer(name, false, depth + 1)
        }
    }

    companion object {
        var depth = 1
        val template = File("data/day14/input.txt").readLines().map {
            val (a, b) = it.split(" -> ")
            a to b
        }.toMap()
        fun hasInsert(name: String) = template.containsKey(name)
        // fixme don't hardcode the keys for the result
        val result: MutableMap<Char, Long> = mutableMapOf('C' to 0L, 'K' to 0L, 'N' to 0L, 'V' to 0L, 'F' to 0L,
            'O' to 0L, 'P' to 0L, 'H' to 0L,'B' to 0L, 'S' to 0L) }
}

fun lunch(word: String, steps: Int) {
    Polymer.depth = steps
    val stack = Stack<Polymer>()
    word.substring(1).windowed(2).reversed().forEach {
        stack.push(Polymer(it))
    }
    // the very first polymer is a right node
    stack.push(Polymer(word.substring(0..1), right = true))

    while (stack.isNotEmpty()) {
        val next = stack.pop()
        if (next.depth < steps) {
            stack.push(next.getLeft())
            stack.push(next.getRight())
        }
        collect(next)
    }
    println(Polymer.result)
}

// handles the pop() of a Polymer. here we increase the count of the letters at the last step
fun collect(polymer: Polymer) {
    if (polymer.depth == Polymer.depth) {
        if (polymer.right) {
            val count0 = Polymer.result.getOrDefault(polymer.name[0], 0L)
            Polymer.result[polymer.name[0]] = count0 + 1L
            val count1 = Polymer.result.getOrDefault(polymer.name[1], 0L)
            Polymer.result[polymer.name[1]] = count1 + 1L
        } else {
            val count = Polymer.result.getOrDefault(polymer.name[1], 0L)
            Polymer.result[polymer.name[1]] = count + 1L
        }
    }
}

// part 1
fun breakfast(start: String) {
    val template = File("data/day14/input.txt").readLines().map {
        val (a, b) = it.split(" -> ")
        Pair(a, a[0] + b + a[1])
    }.toMap()
    var material = start
    for (i in 1..10) {
        material = material.windowed(2).mapIndexed { index, it ->
            var polymer = (if (it in template) template[it] else it).toString()
            if (index == 0) polymer = it[0] + polymer
            polymer.substring(1)
        }.joinToString("")
    }
    val result = material.toList().groupingBy { it }.eachCount()
    println(result.maxOf { it.value } - result.minOf { it.value })
}