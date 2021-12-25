package day24

class ArithmeticLogicUntidException(message:String): Exception(message)

fun main() {
    try {
        breakfast()
    } catch (alue: ArithmeticLogicUntidException) {
        println(alue)
        println("don't worry, ALUs are hard.")
    }
}

fun breakfast() {
    throw ArithmeticLogicUntidException("too hard. revisit later.")
}