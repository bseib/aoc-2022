class Problem5 : DailyProblem<String> {

    data class Operation(val qty: Int, val fromStack: Int, val toStack: Int)

    class Stack {
        val crates = ArrayDeque<Char>()
    }

    class CargoDeck(val stacks: List<Stack>, val ops: List<Operation>) {

        fun executeOperations(preserveOrder: Boolean) {
            if (preserveOrder) {
                ops.forEach { op ->
                    val tmp = Stack()
                    (1..op.qty).forEach {
                        tmp.crates.addLast(stacks[op.fromStack - 1].crates.removeLast())
                    }
                    (1..op.qty).forEach {
                        stacks[op.toStack - 1].crates.addLast(tmp.crates.removeLast())
                    }
                }
            }
            else {
                ops.forEach { op ->
                    (1..op.qty).forEach {
                        stacks[op.toStack - 1].crates.addLast(stacks[op.fromStack - 1].crates.removeLast())
                    }
                }
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
        return toCargoDeck(data0).apply { executeOperations(false) }.getTopCrates()
    }

    override fun solvePart1(): String {
        return toCargoDeck(data1).apply { executeOperations(false) }.getTopCrates()
    }

    override fun solvePart2(): String {
        return toCargoDeck(data1).apply { executeOperations(true) }.getTopCrates()
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

        val data1 = """
        [C] [B] [H]                
[W]     [D] [J] [Q] [B]            
[P] [F] [Z] [F] [B] [L]            
[G] [Z] [N] [P] [J] [S] [V]        
[Z] [C] [H] [Z] [G] [T] [Z]     [C]
[V] [B] [M] [M] [C] [Q] [C] [G] [H]
[S] [V] [L] [D] [F] [F] [G] [L] [F]
[B] [J] [V] [L] [V] [G] [L] [N] [J]
 1   2   3   4   5   6   7   8   9 

move 5 from 4 to 7
move 8 from 5 to 9
move 6 from 2 to 8
move 7 from 7 to 9
move 1 from 7 to 4
move 2 from 7 to 4
move 9 from 8 to 4
move 16 from 9 to 7
move 1 from 3 to 8
move 15 from 4 to 5
move 3 from 9 to 5
move 2 from 3 to 5
move 1 from 8 to 7
move 3 from 1 to 7
move 5 from 3 to 5
move 13 from 7 to 2
move 5 from 7 to 1
move 7 from 2 to 6
move 2 from 7 to 8
move 3 from 6 to 5
move 2 from 8 to 2
move 2 from 6 to 1
move 11 from 1 to 7
move 2 from 2 to 9
move 8 from 6 to 5
move 2 from 9 to 6
move 3 from 6 to 4
move 1 from 4 to 7
move 22 from 5 to 6
move 13 from 6 to 9
move 5 from 2 to 7
move 6 from 5 to 8
move 13 from 7 to 2
move 2 from 4 to 6
move 5 from 6 to 3
move 2 from 7 to 5
move 3 from 3 to 6
move 2 from 6 to 2
move 8 from 2 to 4
move 2 from 4 to 7
move 2 from 2 to 9
move 5 from 4 to 5
move 2 from 3 to 2
move 1 from 5 to 4
move 6 from 5 to 9
move 1 from 7 to 3
move 1 from 5 to 9
move 5 from 5 to 1
move 1 from 6 to 8
move 1 from 5 to 8
move 4 from 6 to 9
move 8 from 8 to 9
move 1 from 3 to 6
move 4 from 1 to 7
move 3 from 6 to 4
move 7 from 2 to 6
move 27 from 9 to 8
move 3 from 4 to 7
move 6 from 8 to 1
move 1 from 4 to 6
move 1 from 2 to 7
move 7 from 6 to 3
move 1 from 4 to 3
move 4 from 1 to 6
move 1 from 9 to 2
move 1 from 2 to 4
move 1 from 4 to 5
move 3 from 9 to 4
move 5 from 7 to 8
move 2 from 5 to 6
move 4 from 6 to 9
move 10 from 8 to 3
move 2 from 4 to 7
move 3 from 1 to 7
move 2 from 9 to 6
move 6 from 3 to 1
move 7 from 3 to 4
move 2 from 1 to 9
move 4 from 1 to 9
move 1 from 3 to 6
move 1 from 3 to 8
move 2 from 9 to 5
move 2 from 5 to 3
move 3 from 3 to 1
move 1 from 4 to 6
move 5 from 7 to 6
move 2 from 3 to 4
move 2 from 8 to 1
move 9 from 4 to 7
move 4 from 9 to 3
move 2 from 8 to 3
move 1 from 1 to 4
move 1 from 6 to 2
move 1 from 2 to 9
move 6 from 3 to 5
move 2 from 1 to 3
move 1 from 3 to 2
move 1 from 2 to 9
move 8 from 6 to 8
move 2 from 6 to 3
move 1 from 1 to 2
move 7 from 7 to 9
move 13 from 8 to 6
move 1 from 2 to 8
move 6 from 9 to 3
move 1 from 1 to 6
move 2 from 8 to 5
move 5 from 3 to 4
move 2 from 8 to 1
move 8 from 5 to 2
move 4 from 3 to 2
move 5 from 8 to 4
move 2 from 9 to 4
move 4 from 4 to 7
move 10 from 2 to 6
move 1 from 2 to 9
move 24 from 6 to 1
move 17 from 1 to 8
move 1 from 9 to 2
move 2 from 4 to 9
move 10 from 7 to 4
move 1 from 2 to 5
move 5 from 9 to 1
move 1 from 7 to 6
move 12 from 8 to 6
move 1 from 7 to 5
move 2 from 5 to 6
move 16 from 6 to 8
move 12 from 1 to 6
move 2 from 1 to 7
move 9 from 6 to 2
move 2 from 4 to 1
move 1 from 1 to 5
move 7 from 4 to 6
move 13 from 8 to 2
move 5 from 8 to 2
move 2 from 7 to 3
move 2 from 4 to 9
move 1 from 5 to 4
move 3 from 9 to 8
move 2 from 4 to 2
move 2 from 3 to 8
move 1 from 1 to 5
move 1 from 4 to 8
move 6 from 2 to 7
move 1 from 5 to 8
move 1 from 6 to 2
move 7 from 6 to 8
move 1 from 6 to 2
move 24 from 2 to 1
move 10 from 8 to 3
move 4 from 8 to 2
move 4 from 7 to 1
move 5 from 2 to 9
move 1 from 6 to 2
move 10 from 3 to 1
move 2 from 7 to 3
move 2 from 3 to 7
move 2 from 7 to 9
move 35 from 1 to 5
move 28 from 5 to 6
move 2 from 2 to 7
move 19 from 6 to 4
move 3 from 1 to 2
move 3 from 2 to 5
move 23 from 4 to 7
move 2 from 6 to 8
move 4 from 7 to 6
move 3 from 5 to 6
move 13 from 7 to 4
move 2 from 5 to 6
move 2 from 9 to 4
move 5 from 6 to 3
move 6 from 4 to 5
move 1 from 4 to 8
move 4 from 4 to 6
move 5 from 9 to 7
move 2 from 8 to 7
move 5 from 3 to 2
move 4 from 5 to 2
move 5 from 2 to 9
move 4 from 8 to 4
move 1 from 9 to 8
move 2 from 2 to 6
move 4 from 4 to 2
move 3 from 2 to 3
move 3 from 5 to 1
move 2 from 3 to 2
move 3 from 1 to 4
move 1 from 9 to 4
move 5 from 4 to 9
move 2 from 4 to 3
move 5 from 6 to 8
move 1 from 9 to 7
move 2 from 6 to 3
move 1 from 4 to 5
move 1 from 9 to 4
move 6 from 8 to 6
move 2 from 3 to 6
move 2 from 9 to 4
move 2 from 3 to 9
move 1 from 3 to 1
move 17 from 6 to 4
move 1 from 1 to 8
move 1 from 6 to 5
move 1 from 9 to 2
move 11 from 4 to 6
move 9 from 4 to 5
move 7 from 9 to 4
move 2 from 5 to 2
move 1 from 4 to 9
move 5 from 2 to 1
move 1 from 2 to 9
move 4 from 4 to 9
move 4 from 1 to 5
move 1 from 1 to 7
move 1 from 8 to 9
move 8 from 7 to 8
move 4 from 7 to 4
move 9 from 5 to 2
move 2 from 4 to 1
move 11 from 6 to 8
move 2 from 4 to 3
move 2 from 4 to 8
move 1 from 1 to 4
move 3 from 2 to 8
move 1 from 1 to 3
move 3 from 3 to 9
move 8 from 9 to 6
move 1 from 4 to 8
move 2 from 9 to 3
move 5 from 6 to 9
move 7 from 5 to 6
move 2 from 3 to 4
move 5 from 7 to 9
move 2 from 4 to 5
move 2 from 2 to 3
move 10 from 9 to 5
move 2 from 6 to 3
move 6 from 2 to 7
move 10 from 5 to 3
move 6 from 7 to 1
move 2 from 1 to 7
move 4 from 3 to 9
move 3 from 8 to 2
move 2 from 7 to 5
move 19 from 8 to 7
move 4 from 5 to 9
move 4 from 9 to 8
move 1 from 2 to 5
move 3 from 6 to 8
move 1 from 5 to 9
move 5 from 9 to 7
move 6 from 3 to 8
move 1 from 3 to 8
move 2 from 3 to 2
move 23 from 7 to 6
move 10 from 8 to 4
move 4 from 4 to 9
move 4 from 2 to 6
move 1 from 3 to 8
move 4 from 8 to 4
move 31 from 6 to 4
move 9 from 4 to 5
move 8 from 5 to 3
move 1 from 6 to 7
move 2 from 5 to 7
move 4 from 9 to 2
move 21 from 4 to 8
move 4 from 2 to 9
move 3 from 3 to 9
move 2 from 7 to 9
move 11 from 4 to 9
move 1 from 8 to 5
move 1 from 5 to 9
move 9 from 9 to 3
move 3 from 1 to 5
move 2 from 5 to 8
move 11 from 3 to 6
move 4 from 6 to 3
move 2 from 8 to 3
move 10 from 9 to 6
move 22 from 8 to 9
move 1 from 1 to 8
move 4 from 6 to 3
move 2 from 7 to 6
move 3 from 8 to 3
move 14 from 3 to 2
move 1 from 3 to 4
move 1 from 2 to 4
move 2 from 9 to 1
move 1 from 5 to 7
move 1 from 3 to 2
move 14 from 6 to 5
move 13 from 5 to 2
move 1 from 5 to 6
move 1 from 7 to 9
move 8 from 9 to 4
move 2 from 6 to 7
move 23 from 2 to 4
move 2 from 1 to 4
move 2 from 2 to 5
move 1 from 5 to 1
move 1 from 7 to 2
move 1 from 5 to 9
move 16 from 9 to 5
move 1 from 2 to 4
move 13 from 5 to 3
move 1 from 1 to 4
move 1 from 7 to 1
move 1 from 5 to 3
move 2 from 5 to 7
move 2 from 7 to 1
move 9 from 3 to 2
move 2 from 1 to 7
move 1 from 1 to 9
move 19 from 4 to 2
move 1 from 9 to 7
move 1 from 7 to 8
move 23 from 2 to 8
move 2 from 7 to 2
move 12 from 4 to 5
move 12 from 5 to 1
move 5 from 2 to 9
move 2 from 2 to 7
move 5 from 8 to 1
move 3 from 9 to 4
move 1 from 2 to 8
move 1 from 2 to 4
move 4 from 8 to 1
move 2 from 3 to 1
move 2 from 7 to 5
move 1 from 4 to 9
move 8 from 4 to 7
move 13 from 8 to 6
move 1 from 3 to 1
move 13 from 6 to 7
move 13 from 7 to 6
move 7 from 1 to 4
move 5 from 7 to 3
move 3 from 4 to 3
move 13 from 6 to 1
move 3 from 8 to 6
move 8 from 3 to 8
move 12 from 1 to 8
move 1 from 3 to 5
move 6 from 1 to 7
move 3 from 6 to 8
move 1 from 3 to 8
move 1 from 9 to 2
move 3 from 5 to 6
move 1 from 7 to 3
move 8 from 7 to 1
move 2 from 6 to 2
move 3 from 4 to 3
move 2 from 9 to 2
move 6 from 8 to 9
move 5 from 2 to 5
move 2 from 3 to 4
move 5 from 5 to 4
move 1 from 3 to 9
move 8 from 4 to 5
move 1 from 6 to 8
move 2 from 1 to 4
move 1 from 1 to 4
move 3 from 1 to 5
move 3 from 1 to 6
move 7 from 1 to 9
move 2 from 6 to 9
move 1 from 3 to 5
move 17 from 8 to 7
move 17 from 7 to 6
move 5 from 5 to 2
move 5 from 2 to 1
move 13 from 6 to 2
move 1 from 1 to 4
move 5 from 5 to 1
move 1 from 1 to 5
move 10 from 9 to 1
move 13 from 1 to 8
move 13 from 8 to 4
move 5 from 6 to 7
move 8 from 1 to 7
move 1 from 1 to 3
move 12 from 2 to 6
move 1 from 3 to 8
move 6 from 6 to 2
move 2 from 5 to 1
move 5 from 2 to 5
move 2 from 5 to 9
move 12 from 4 to 2
move 1 from 6 to 2
move 15 from 2 to 1
move 1 from 8 to 6
move 2 from 7 to 3
move 2 from 4 to 2
move 1 from 2 to 9
move 1 from 2 to 6
move 7 from 7 to 3
move 1 from 4 to 1
move 17 from 1 to 2
move 3 from 6 to 4
move 1 from 3 to 8
move 3 from 9 to 6
move 4 from 6 to 3
move 13 from 2 to 9
move 3 from 2 to 8
move 2 from 5 to 1
move 6 from 8 to 2
move 1 from 6 to 2
move 3 from 2 to 7
move 3 from 1 to 6
move 2 from 9 to 8
move 6 from 9 to 8
move 8 from 9 to 3
move 7 from 7 to 4
move 20 from 3 to 7
move 4 from 6 to 8
move 1 from 8 to 6
move 2 from 6 to 4
move 3 from 2 to 1
move 2 from 9 to 6
move 9 from 8 to 6
move 3 from 1 to 9
move 9 from 4 to 8
move 1 from 5 to 6
move 3 from 4 to 2
move 1 from 5 to 3
move 8 from 6 to 4
move 4 from 9 to 3
move 10 from 8 to 6
move 5 from 2 to 3
move 3 from 6 to 4
move 10 from 3 to 1
move 11 from 4 to 1
move 1 from 8 to 2
move 2 from 4 to 2
move 1 from 4 to 9
move 10 from 6 to 3
move 21 from 1 to 5
move 2 from 2 to 7
move 1 from 9 to 6
move 1 from 6 to 3
move 1 from 6 to 7
move 11 from 5 to 6
move 1 from 2 to 8
move 1 from 5 to 9
move 11 from 6 to 3
move 1 from 8 to 4
move 1 from 4 to 1
move 3 from 5 to 7
move 1 from 1 to 5
move 5 from 5 to 8
move 23 from 7 to 9
move 5 from 8 to 4
move 1 from 5 to 2
move 12 from 3 to 4
move 6 from 3 to 6
move 1 from 5 to 2
move 8 from 9 to 2
move 1 from 7 to 8
move 2 from 7 to 9
move 4 from 3 to 5
move 1 from 5 to 9
move 1 from 6 to 5
move 4 from 6 to 5
move 3 from 2 to 1
move 3 from 1 to 3
move 8 from 9 to 1
move 4 from 2 to 9
move 1 from 9 to 7
move 14 from 4 to 8
move 3 from 3 to 4
move 1 from 5 to 8
move 2 from 8 to 6
move 2 from 6 to 7
move 4 from 4 to 3
move 12 from 9 to 1
move 1 from 3 to 2
move 6 from 8 to 2
move 1 from 7 to 1
move 5 from 2 to 3
move 21 from 1 to 3
move 5 from 5 to 4
move 1 from 8 to 5
move 2 from 2 to 7
move 1 from 6 to 1
move 2 from 9 to 2
move 1 from 2 to 9
move 1 from 1 to 5
move 4 from 3 to 5
move 7 from 8 to 1
move 6 from 1 to 9
move 1 from 2 to 5
move 6 from 9 to 7
move 8 from 3 to 4
move 2 from 4 to 8
move 1 from 1 to 6
move 10 from 3 to 9
move 12 from 4 to 2
move 1 from 8 to 1
        """.trimIndent().toLines()

    }

}