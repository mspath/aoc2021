package day21

// this went over my head, but it turned out to be a primer in dynamic programming
// see https://www.youtube.com/watch?v=a6ZdJEntKkkd

fun countWins(state: State): Pair<Long, Long> {
    if (state.score1 >= 21) return Pair(1L, 0L)
    if (state.score2 >= 21) return Pair(0L, 1L)
    if (Store.store.contains(state)) return Store.store.get(state)!!
    var answer = Pair(0L, 0L)
    for (d1 in 1..3) {
        for (d2 in 1..3) {
            for (d3 in 1..3) {
                val roll = d1 + d2 + d3
                val newPosition1 = if ((state.position1 + roll) % 10 == 0) 10 else (state.position1 + roll) % 10
                val newScore1 = state.score1 + newPosition1
                val wins: Pair<Long, Long> = countWins(State(state.position2, newPosition1, state.score2, newScore1))
                answer = Pair(answer.first + wins.second, answer.second + wins.first)
            }
        }
    }
    Store.store.put(state, answer)
    return answer
}