package day12

import java.io.File

fun main() {
    breakfast()
    lunch()
}

object G {
    val graph by lazy {
        val vertices = File("data/day12/input.txt").readLines()

        // a map of all edges to their connected edges
        val graph: MutableMap<String, Set<String>> = mutableMapOf()
        vertices.forEach {
            val (a, b) = it.split("-")
            val nodes = graph.getOrDefault(a, emptySet())
            graph[a] = nodes + setOf(b) - setOf("start")
            val nodes2 = graph.getOrDefault(b, emptySet())
            graph[b] = nodes2 +  setOf(a) - setOf("start")
        }
        graph.toMap()
    }
}

// part 1

fun countPathsBreakfast(map: Map<String, Set<String>>, path: List<String>): Long {
    val nodes = map[path.last()] ?: return 0
    return nodes.sumOf { node ->
        if (node == "end") return@sumOf 1
        if (node.all { it.isLowerCase() } && path.contains(node)) {
            return@sumOf 0
        }
        countPathsBreakfast(map, path + node)
    }
}

fun breakfast() {
    val result = countPathsBreakfast(G.graph, listOf("start"))
    println(result)
}

// part 2

fun countPathsLunch(map: Map<String, Set<String>>, path: List<String>, anotherVisit: Boolean = true): Long {
    val nodes = map[path.last()] ?: return 0
    return nodes.sumOf { node ->
        if (node == "end") return@sumOf 1
        if (node.all { it.isLowerCase() } && path.contains(node)) {
            if (!anotherVisit) return@sumOf 0
            else return@sumOf countPathsLunch(map, path + node, false)
        }
        countPathsLunch(map, path + node, anotherVisit)
    }
}

fun lunch() {
    val result = countPathsLunch(G.graph, listOf("start"))
    println(result)
}