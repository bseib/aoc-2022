
fun String.toLines() = this.trimIndent().split("\n").map { it.trim() }
