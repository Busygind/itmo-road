package ru.itmo

fun main() {
    val lfsr1 = LFSR(listOf(6, 5, 3, 0), 89)
    val lfsr2 = LFSR(listOf(8, 5, 1, 0), 91)

    println("Введите текст для кодирования:")

    var input = readLine() ?: ""
    var lines = input
    while (input != "") {
        input = readLine() ?: ""
        lines += "\n" + input
    }
    val encodedText = codeText(lines, lfsr1, lfsr2)
    println("Закодированный текст: $encodedText")

    // Сбрасываем наши ЛРС для декодирования
    lfsr1.reset()
    lfsr2.reset()

    val decodedText = codeText(encodedText, lfsr1, lfsr2)
    println("Декодированный текст: $decodedText")
}

fun codeText(text: String, lfsr1: LFSR, lfsr2: LFSR): String {
    val result = StringBuilder()

    for (char in text) {
        var encodedChar = char.toInt()
        for (i in 0 until 8) {
            // Получаем следующий бит из каждого ЛРС
            val bit1 = lfsr1.nextBit()
            val bit2 = lfsr2.nextBit()

            // Используем XOR для комбинирования битов
            val keyBit = bit1 xor bit2

            // Применяем XOR для шифрования/дешифрования
            val bit = (encodedChar shr i) and 1
            val encryptedBit = bit xor keyBit

            // Собираем новый закодированный символ
            encodedChar = (encodedChar and (1 shl i).inv()) or (encryptedBit shl i)
        }
        result.append(encodedChar.toChar())
    }

    return result.toString()
}

class LFSR(private val taps: List<Int>, private val length: Int) {
    private var state = (1 shl (length - 1)) - 1
    private val initialState = state

    fun nextBit(): Int {
        var newBit = 0
        for (tap in taps) {
            newBit = newBit xor ((state shr tap) and 1)
        }
        state = (state shr 1) or (newBit shl (length - 1))
        return newBit
    }

    fun reset() {
        state = initialState
    }
}