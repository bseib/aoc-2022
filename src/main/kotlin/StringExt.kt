
fun String.toTrimmedLines() = this.trimIndent().split("\n").map { it.trim() }
fun String.toLines() = this.trimIndent().split("\n")
