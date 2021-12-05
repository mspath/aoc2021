package day4

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import java.io.File

data class Field(val value: Int, var found: Boolean = false)

class Board(val rows: List<List<Field>>, val cols: List<List<Field>>) {

    var active = true

    fun checkBingo(): Boolean {
        for (row in rows) {
            val bingo = row.all {
                it.found == true
            }
            if (bingo) {
                println("bingo")
                println(row)
                println("result: ${calculateResult()}")
                active = false
                return true
            }
        }
        for (col in cols) {
            val bingo = col.all {
                it.found == true
            }
            if (bingo) {
                println("bingo")
                println(col)
                println("result: ${calculateResult()}")
                active = false
                return true
            }
        }
        return false
    }

    // add up all values which have not been crossed out
    // the main app needs to mulitply it with the last ball
    fun calculateResult(): Int {
        return rows.sumOf {
            it.filter { !it.found }.map { it.value }.sum()
        }
    }

    companion object {
        // generate columns for easy lookup
        fun generateColumns(rows: List<List<Field>>): List<List<Field>> {
            return (0..4).map { col ->
                (0..4).map { row ->
                    Bingo.table[rows[row][col].value]
                }
            }
        }
    }
}

object Bingo {

    val table: List<Field> = buildList {
        for (i in 0 until 100) {
            add(Field(i))
        }
    }

    var boards: MutableList<Board> = mutableListOf()

    fun nextBall(ball: Int) {
        println(ball)
        table[ball].found = true
        boards.forEach {
            if (it.active) it.checkBingo()
        }
    }

    fun emitBalls(balls: List<Int>): Flow<Int> = flow {
        balls.forEach {
            emit(it)
            delay(50)
        }
    }
}

fun lunch() {
    val balls = File("data/day4/input_balls.txt").readText()
        .trim()
        .split(",")
        .map { it.toInt() }
    val boards = File("data/day4/input_boards.txt").readText().trim().split("\n\n")
    boards.forEach {
        val rows = it.split("\n").map {
            it.trim()
                .replace("""\s+""".toRegex(), " ")
                .split(" ")
                .map { Bingo.table[it.toInt()] }
        }
        val cols = Board.generateColumns(rows)
        Bingo.boards.add(Board(rows, cols))
    }

    runBlocking {
        Bingo.emitBalls(balls).collect {
            Bingo.nextBall(it)
        }
    }
}
