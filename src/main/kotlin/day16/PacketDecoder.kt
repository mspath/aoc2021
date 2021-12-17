package day16

import java.io.File

fun main() {
    //breakfast()
    lunch()
}

fun Char.encode() = when(this) {
    '0' -> "0000"
    '1' -> "0001"
    '2' -> "0010"
    '3' -> "0011"
    '4' -> "0100"
    '5' -> "0101"
    '6' -> "0110"
    '7' -> "0111"
    '8' -> "1000"
    '9' -> "1001"
    'A' -> "1010"
    'B' -> "1011"
    'C' -> "1100"
    'D' -> "1101"
    'E' -> "1110"
    'F' -> "1111"
    else -> "*"
}

fun typeIdInfo(id: Int): String {
    assert(id in 0..7)
    val info = when (id) {
        0 -> "sum"
        1 -> "product"
        2 -> "min"
        3 -> "max"
        4 -> "literal"
        5 -> "greater"
        6 -> "less"
        7 -> "equals"
        else -> "panic"
    }
    return info
}

val opSum: List<Long>.() -> Long = { this.sum() }
val opProduct: List<Long>.() -> Long = { this.map { it }.reduce { acc, value -> acc * value } }
val opMin: List<Long>.() -> Long = { this.minOf { it } }
val opMax: List<Long>.() -> Long = { this.maxOf { it } }
val opGreater: List<Long>.() -> Long = { if (this.first() > this.last()) 1L else 0L }
val opLess: List<Long>.() -> Long = { if (this.first() < this.last()) 1L else 0L }
val opEqual: List<Long>.() -> Long = { if (this.toSet().size == 1) 1L else 0L }

object PacketDecoder {
    var P = 0
    val T by lazy {
        File("data/day16/input.txt").readText().map { it.encode() }.joinToString("")
    }
    // for part I
    // var sum = 0
}

fun processLiteral(): Long {
    var value = ""
    // read chunks of 5 bits until one starts with 0
    while (PacketDecoder.T[PacketDecoder.P] == '1') {
        value += PacketDecoder.T.substring(PacketDecoder.P + 1, PacketDecoder.P + 5)
        PacketDecoder.P += 5
    }
    value += PacketDecoder.T.substring(PacketDecoder.P + 1, PacketDecoder.P + 5)
    PacketDecoder.P += 5
    return value.toLong(2)
}

fun processTypeOperator(operation: Int): Long {
    val lengthTypeId = PacketDecoder.T[PacketDecoder.P]
    PacketDecoder.P += 1
    val values: MutableList<Long> = mutableListOf()
    when (lengthTypeId) {
        '0' -> {
            // length of sub packets in bits
            val length = PacketDecoder.T.substring(PacketDecoder.P, PacketDecoder.P + 15).toInt(2)
            PacketDecoder.P += 15
            val last = PacketDecoder.P + length
            while (PacketDecoder.P < last) {
                values.add(processPacket())
            }
        }
        '1' -> {
            // number of contained sub packets
            val count = PacketDecoder.T.substring(PacketDecoder.P, PacketDecoder.P + 11).toInt(2)
            PacketDecoder.P += 11
            (0 until count).forEach {
                values.add(processPacket())
            }
        }
    }
    return when (operation) {
        0 -> opSum(values)
        1 -> opProduct(values)
        2 -> opMin(values)
        3 -> opMax(values)
        5 -> opGreater(values)
        6 -> opLess(values)
        7 -> opEqual(values)
        else -> 0L
    }
}

fun processPacket(): Long {
    val version = PacketDecoder.T.substring(PacketDecoder.P, PacketDecoder.P + 3)
    val type = PacketDecoder.T.substring(PacketDecoder.P + 3, PacketDecoder.P + 6)
    // for part 1
    // PacketDecoder.sum += version.toInt(2)
    PacketDecoder.P += 6
    if (type == "100") {
        return processLiteral()
    } else {
        return processTypeOperator(type.toInt(2))
    }
}

//fun breakfast() {
//    processPacket()
//    println(PacketDecoder.sum)
//}

fun lunch() {
    println(processPacket())
}