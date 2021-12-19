package day18

class SnailfishMathError(message:String): Exception(message)

fun main() {
    try {
        breakfast()
    } catch (sme: SnailfishMathError) {
        println(sme)
        println("don't worry, snailfish math is hard.")
    }
}

fun breakfast() {
    throw SnailfishMathError("too hard. revisit later.")
}