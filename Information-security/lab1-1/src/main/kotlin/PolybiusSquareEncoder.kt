fun encodePolybiusSquare(message: String, square: Map<Char, Pair<Int, Int>>): String {
    val encodedMessage = StringBuilder()

    for (char in message) {
        if (char in square) {
            val (row, col) = square[char] ?: continue
            encodedMessage.append("$row$col ")
        }
    }

    return encodedMessage.toString().trim()
}