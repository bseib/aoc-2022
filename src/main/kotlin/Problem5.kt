class Problem5 : DailyProblem<String> {

    data class Operation(val qty: Int, val fromStack: Int, val toStack: Int)

    class Stack {
        val crates = ArrayDeque<Char>()
    }

    class CargoDeck(val stacks: List<Stack>, val ops: List<Operation>) {

        fun executeOperations() {
//            println(this)
//            println()
            ops.forEach { op ->
                (1..op.qty).forEach {
                    stacks[op.toStack - 1].crates.addLast(stacks[op.fromStack - 1].crates.removeLast())
                }
//                println(this)
//                println()
            }
        }

        fun getTopCrates(): String {
            return stacks.map { it.crates.lastOrNull() ?: " " }.joinToString("")
        }

        override fun toString(): String {
            val height = stacks.maxOf { it.crates.size }
            val crateAt = { s: Stack, idx: Int ->
                if (idx >= s.crates.size) "   " else "[${s.crates[idx]}]"
            }
            return (1..height).map { h ->
                stacks.map { crateAt(it, h - 1) }.joinToString(" ")
            }
                .reversed()
                .joinToString("\n")
        }
    }

    fun solvePart0(): String {
        return toCargoDeck(data0).apply { executeOperations() }.getTopCrates()
    }

    override fun solvePart1(): String {
        return ""
    }

    override fun solvePart2(): String {
        return ""
    }

    fun toCargoDeck(data: List<String>): CargoDeck {
        val separator = data.indexOfFirst { it == "" }
        val stacks = parseStacks(data.subList(0, separator - 1))
        val ops = parseOperations(data.subList(1 + separator, data.size))
        return CargoDeck(stacks, ops)
    }

    private fun parseOperations(data: List<String>): List<Operation> {
        val opRegex = "^move\\s+(\\d+)\\s+from\\s+(\\d+)\\s+to\\s+(\\d+)$".toRegex()
        return data.map { line ->
            val intValues = opRegex.matchEntire(line)?.groupValues?.takeLast(3)?.map { it.toInt() }
                ?: throw Exception("operation did not match regex")
            Operation(intValues[0], intValues[1], intValues[2])
        }
    }

    private fun parseStacks(data: List<String>): List<Stack> {
        val stacks = (1..((data.first().length + 1) / 4)).map { Stack() }
        data.forEach { line ->
            line.chunked(4)
                .map { it[1] }
                .forEachIndexed { index, c -> if (c != ' ') stacks[index].crates.addFirst(c) }
        }
        return stacks
    }

    companion object {
        val data0 = """
            |    [D]    
            |[N] [C]    
            |[Z] [M] [P]
            | 1   2   3 
            |
            |move 1 from 2 to 1
            |move 3 from 1 to 3
            |move 2 from 2 to 1
            |move 1 from 1 to 2
        """.trimMargin().toLines()

    }

}