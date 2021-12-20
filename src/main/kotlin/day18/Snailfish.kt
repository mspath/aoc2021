package day18

class SnailfishMathException(message:String): Exception(message)

fun main() {
    try {
        breakfast()
    } catch (sme: SnailfishMathException) {
        println(sme)
        println("don't worry, snailfish math is hard.")
    }
}

fun breakfast() {
    throw SnailfishMathException("too hard. revisit later.")
}