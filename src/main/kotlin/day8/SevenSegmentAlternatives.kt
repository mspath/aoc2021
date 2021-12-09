package day8

val letters = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g')

fun play() {
    val candidates: Map<Segment, MutableSet<Char>> = mapOf(
        Segment.A to letters.toMutableSet(),
        Segment.B to letters.toMutableSet(),
        Segment.C to letters.toMutableSet(),
        Segment.D to letters.toMutableSet(),
        Segment.E to letters.toMutableSet(),
        Segment.F to letters.toMutableSet(),
        Segment.G to letters.toMutableSet()
    )
    println(candidates)
    val words = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab"

    // we know that the candidates for each segment must be from letters from words with the size of the segment
    // lets just do it for 1 which has length 2:
//    val digitsOrder2 = Digit.values().filter { it.segments.size == 2 }
//    println(digitsOrder2)
//    val wordsOrder2 = words.split(" ").filter { it.length == 2 }
//    println(wordsOrder2)
//    val lettersOrder2 = wordsOrder2.joinToString("").trim().toSet()
//    println(lettersOrder2)
//    digitsOrder2.forEach { digit ->
//        digit.segments.forEach {
//            val canditate = candidates.get(it)
//            val diff = canditate?.filter { it !in lettersOrder2 }
//            diff?.let {
//                canditate.removeAll(it)
//            }
//        }
//    }

}
