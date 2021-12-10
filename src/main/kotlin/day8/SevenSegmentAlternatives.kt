package day8

val letters = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g')

fun play() {

    // one idea is to keep track of the 'canditates' and remove them as info becomes processed
    val candidates: Map<Segment, MutableSet<Char>> = mapOf(
        Segment.A to letters.toMutableSet(),
        Segment.B to letters.toMutableSet(),
        Segment.C to letters.toMutableSet(),
        Segment.D to letters.toMutableSet(),
        Segment.E to letters.toMutableSet(),
        Segment.F to letters.toMutableSet(),
        Segment.G to letters.toMutableSet()
    )

    // another idea is to brute force via bitwise operations
    // all letters add up to the digit so we can try and eleminate as they are processed
    val segmentToBinary: Map<Segment, Int> = mapOf(
        Segment.A to 0b00000001,
        Segment.B to 0b00000010,
        Segment.C to 0b00000100,
        Segment.D to 0b00001000,
        Segment.E to 0b00010000,
        Segment.F to 0b00100000,
        Segment.G to 0b01000000
    )
    val digitToBinary: Map<Digit, Int> = mapOf(
        Digit.ZERO to 0b01110111,
        Digit.ONE to 0b00100100,
        Digit.TWO to 0b01011101,
        Digit.THREE to 0b01101101,
        Digit.FOUR to 0b00101110,
        Digit.FIVE to 0b01101011,
        Digit.SIX to 0b01111011,
        Digit.SEVEN to 0b00100101,
        Digit.EIGHT to 0b01111111,
        Digit.NINE to 0b01101111
    )

    val sample = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab"

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
