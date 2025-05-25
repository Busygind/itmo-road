fun decodeByPolybiusSquare(lines: List<String>, matrix: Array<CharArray>): List<String> {
    val decoded = mutableListOf<String>()

    lines.forEach {
        var line = ""
        it.split(" ").forEach { digits ->
            line += matrix[digits.first().digitToInt() - 1][digits.last().digitToInt() - 1]
        }
        decoded.add(line)
    }

    return decoded
}