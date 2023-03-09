class Problem11 : DailyProblem<Long> {

    sealed class OpNumber {
        val cache = mutableMapOf<Long, Long>()
        fun cache(mod: Long, lambda: () -> Long) = cache.computeIfAbsent(mod) { lambda() }
        abstract fun evaluateModulo(mod: Long): Long
        abstract fun evaluate(): Long
    }

    class OpLongNumber(val n: Long) : OpNumber() {
        override fun evaluateModulo(mod: Long) = cache(mod) { n % mod }
        override fun evaluate() = n
    }

    class OpThirdFloorNumber(val n: OpNumber) : OpNumber() {
        override fun evaluateModulo(mod: Long) = cache(mod) { n.evaluate() / 3 }
        override fun evaluate() = n.evaluate() / 3
    }

    class OpAddNumber(val n: OpNumber, val operand: Long) : OpNumber() {
        override fun evaluateModulo(mod: Long) = cache(mod) { (n.evaluateModulo(mod) + operand) % mod }
        override fun evaluate() = n.evaluate() + operand
    }

    class OpMultNumber(val n: OpNumber, val operand: Long) : OpNumber() {
        override fun evaluateModulo(mod: Long) = cache(mod) { (n.evaluateModulo(mod) * operand) % mod  }
        override fun evaluate() = n.evaluate() * operand
    }

    class OpSquareNumber(val n: OpNumber) : OpNumber() {
        override fun evaluateModulo(mod: Long) = cache(mod) { (n.evaluateModulo(mod) * n.evaluateModulo(mod)) % mod }
        override fun evaluate() = n.evaluate() * n.evaluate()
    }

    class Monkey(
        val items: MutableList<OpNumber>,
        val operation: (old: OpNumber) -> OpNumber,
        val modTest: Long,
        val trueDestination: Int,
        val falseDestination: Int,
    ) {
        var inspectionCount = 0L
        fun inspectAndToss(monkeys: List<Monkey>, worryFunction: (OpNumber) -> OpNumber) {
            items.forEach {
                val newItem = worryFunction(operation(it))
                val recipient = when (newItem.evaluateModulo(modTest) % modTest) {
                    0L -> trueDestination
                    else -> falseDestination
                }
                monkeys[recipient].items.add(newItem)
                inspectionCount++
            }
            items.clear()
        }
    }

    fun doInspections(monkeys: List<Monkey>, rounds: Int, worryFunction: (OpNumber) -> OpNumber) {
        repeat(rounds) {
            monkeys.forEach { it.inspectAndToss(monkeys, worryFunction) }
//            if ((it + 1) % 1000 == 0 || it == 19) {
//                println("After round ${1 + it}")
////                println(monkeyItemsToString(monkeys))
//                println(monkeyInspectionsToString(monkeys))
//                println()
//            }
        }
    }

    fun computeMonkeyBusiness(monkeys: List<Monkey>): Long {
        return monkeys.sortedBy { it.inspectionCount }.takeLast(2).map { it.inspectionCount }.product()
    }

    fun monkeyItemsToString(monkeys: List<Monkey>): String {
        return monkeys.mapIndexed { index, monkey ->
            "Monkey $index: ${monkey.items.joinToString(", ")}"
        }.joinToString("\n")
    }

    fun monkeyInspectionsToString(monkeys: List<Monkey>): String {
        return monkeys.mapIndexed { index, monkey ->
            "Monkey $index inspected items ${monkey.inspectionCount} times."
        }.joinToString("\n")
    }

    fun solvePart0(): Long {
        val monkeys = toMonkeyList(data0)
        doInspections(monkeys, 20, thirdFloor)
        return computeMonkeyBusiness(monkeys)
    }

    override fun solvePart1(): Long {
        val monkeys = toMonkeyList(data1)
        doInspections(monkeys, 20, thirdFloor)
        return computeMonkeyBusiness(monkeys)
    }

    fun solvePart0a(): Long {
        val monkeys = toMonkeyList(data0)
        doInspections(monkeys, 10000, unbridled)
        return computeMonkeyBusiness(monkeys)
    }

    override fun solvePart2(): Long {
        val monkeys = toMonkeyList(data1)
        doInspections(monkeys, 10000, unbridled)
        return computeMonkeyBusiness(monkeys)
    }

    // worry functions
    private val thirdFloor = { n: OpNumber -> OpThirdFloorNumber(n) }
    private val unbridled = { n: OpNumber -> n }

    // operations
    private fun makeOpAdd(operand: Long) = { old: OpNumber -> OpAddNumber(old, operand) }
    private fun makeOpMultiply(operand: Long) = { old: OpNumber -> OpMultNumber(old, operand) }
    private fun makeOpSquare() = { old: OpNumber -> OpSquareNumber(old) }

    fun toMonkeyList(data: List<String>): List<Monkey> {
        return data.chunked(7).map { chunk ->
            val stanza = chunk.map { it.trim() }
            val items = stanza[1].substring("Starting items: ".length).split(", ").map { OpLongNumber(it.toLong()) }
            val mod = stanza[3].substring("Test: divisible by ".length).toLong()
            val opString = stanza[2].substring("Operation: new = old ".length)
            val operation = if (opString == "* old") {
                makeOpSquare()
            }
            else if (opString.startsWith("*")) {
                makeOpMultiply(opString.substring(2).toLong())
            }
            else if (opString.startsWith("+")) {
                makeOpAdd(opString.substring(2).toLong())
            }
            else throw Error("Unknown ${stanza[2]}")
            val trueDestination = stanza[4].substring("If true: throw to monkey ".length).toInt()
            val falseDestination = stanza[5].substring("If false: throw to monkey ".length).toInt()
            Monkey(items.toMutableList(), operation, mod, trueDestination, falseDestination)
        }
    }


    companion object {
        val data0 = """
            Monkey 0:
              Starting items: 79, 98
              Operation: new = old * 19
              Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3
            
            Monkey 1:
              Starting items: 54, 65, 75, 74
              Operation: new = old + 6
              Test: divisible by 19
                If true: throw to monkey 2
                If false: throw to monkey 0
            
            Monkey 2:
              Starting items: 79, 60, 97
              Operation: new = old * old
              Test: divisible by 13
                If true: throw to monkey 1
                If false: throw to monkey 3
            
            Monkey 3:
              Starting items: 74
              Operation: new = old + 3
              Test: divisible by 17
                If true: throw to monkey 0
                If false: throw to monkey 1
        """.toLines()
    }

    val data1 = """
        Monkey 0:
          Starting items: 57, 58
          Operation: new = old * 19
          Test: divisible by 7
            If true: throw to monkey 2
            If false: throw to monkey 3
        
        Monkey 1:
          Starting items: 66, 52, 59, 79, 94, 73
          Operation: new = old + 1
          Test: divisible by 19
            If true: throw to monkey 4
            If false: throw to monkey 6
        
        Monkey 2:
          Starting items: 80
          Operation: new = old + 6
          Test: divisible by 5
            If true: throw to monkey 7
            If false: throw to monkey 5
        
        Monkey 3:
          Starting items: 82, 81, 68, 66, 71, 83, 75, 97
          Operation: new = old + 5
          Test: divisible by 11
            If true: throw to monkey 5
            If false: throw to monkey 2
        
        Monkey 4:
          Starting items: 55, 52, 67, 70, 69, 94, 90
          Operation: new = old * old
          Test: divisible by 17
            If true: throw to monkey 0
            If false: throw to monkey 3
        
        Monkey 5:
          Starting items: 69, 85, 89, 91
          Operation: new = old + 7
          Test: divisible by 13
            If true: throw to monkey 1
            If false: throw to monkey 7
        
        Monkey 6:
          Starting items: 75, 53, 73, 52, 75
          Operation: new = old * 7
          Test: divisible by 2
            If true: throw to monkey 0
            If false: throw to monkey 4
        
        Monkey 7:
          Starting items: 94, 60, 79
          Operation: new = old + 2
          Test: divisible by 3
            If true: throw to monkey 1
            If false: throw to monkey 6
    """.toLines()
}