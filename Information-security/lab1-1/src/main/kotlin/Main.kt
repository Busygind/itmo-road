import base.alphabet
import base.decodedFileName
import base.encodedFileName
import base.inputFileName
import base.squareFileName
import base.squareSize
import kotlin.math.ceil
import kotlin.math.sqrt

fun main() {
    println("Напишите одну из следующих команд:\n- encode чтобы закодировать текст\n- decode чтобы декодировать текст")
    val line = readln()

    if (line == "encode") {
        val messageLines = readTextFromFile(inputFileName)
        val polybiusSquare = generatePolybiusSquare()
        writeTextToFile(
            encodedFileName,
            messageLines.map {
                encodePolybiusSquare(it, polybiusSquare)
            }
        )
    } else if (line == "decode") {
        val messageLines = readTextFromFile(encodedFileName)
        val matrix = extractMatrixFromFile(squareFileName, squareSize)

        writeTextToFile(
            decodedFileName,
            decodeByPolybiusSquare(messageLines, matrix)
        )
    } else {
        println("Неизвестная команда, ничего не произошло.")
    }
}

fun generatePolybiusSquare(): Map<Char, Pair<Int, Int>> {
    clearFile(squareFileName)
    val square = mutableMapOf<Char, Pair<Int, Int>>()

    genMatrix(squareSize, alphabet)

    alphabet.forEachIndexed { i, c ->
        val row = i / squareSize + 1
        val col = i % squareSize + 1
        square[c] = Pair(row, col)
    }

    return square
}

object base {
    val alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя ,.:?!'-()".toCharArray().also { it.shuffle() }
    val squareSize = ceil(sqrt(alphabet.size.toDouble())).toInt()
    val squareFileName = "square.txt"
    val inputFileName = "input.txt"
    val encodedFileName = "encoded.txt"
    val decodedFileName = "decoded.txt"
}
