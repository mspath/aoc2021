package day21

fun main() {
    //breakfast()
    lunch()
}

object Dices {
    var counter = 0
    var side = 0
    fun next(): Int {
        counter++
        side++
        if (side == 101) side = 1
        return side
    }
    fun next3(): Int {
        return next() + next() + next()
    }
}

fun breakfast() {
    val goal = 1000
    var position1 = 8
    var position2 = 6
    var score1 = 0
    var score2 = 0

    while(true) {
        var roll = Dices.next3()
        position1 += roll
        position1 = if (position1 % 10 == 0) 10 else position1 % 10
        score1 += position1
        if (score1 >= goal) break
        roll = Dices.next3()
        position2 += roll
        position2 = if (position2 % 10 == 0) 10 else position2 % 10
        score2 += position2
        if (score2 >= goal) break
    }

    val result = minOf(score1, score2) * Dices.counter
    println(result)
}

data class State(val position1: Int, val position2: Int, val score1: Int, val score2: Int)

object Store { val store: MutableMap<State, Pair<Long, Long>> = mutableMapOf() }

fun lunch() {
    val wins = countWins(State(4, 8, 0, 0))
    println(wins)
}
