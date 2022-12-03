class Problem3 : DailyProblem {

    @JvmInline
    value class Item(val name: Char) {
        val priority
            get() = when (name) {
                in 'a'..'z' -> 1 + name.minus('a')
                in 'A'..'Z' -> 27 + name.minus('A')
                else -> throw Exception("Expecting char in set [a-zA-Z]")
            }
    }

    class Knapsack(contents: String) {
        val compartment1: Set<Item>
        val compartment2: Set<Item>

        init {
            val halfSize = contents.length / 2
            compartment1 = contents.take(halfSize).toSet().map { Item(it) }.toSet()
            compartment2 = contents.takeLast(halfSize).toSet().map { Item(it) }.toSet()
        }

        fun findCommonItem(): Item {
            val common = compartment1 intersect compartment2
            require(common.size == 1) {
                throw Exception("expecting only one item in common")
            }
            return common.first()
        }
    }


    fun solvePart0(): Int {
        return data0.sumOf { Knapsack(it).findCommonItem().priority }
    }

    override fun solvePart1(): Int {
        return 0
    }

    override fun solvePart2(): Int {
        return 0
    }

    companion object {
        val data0 = """
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw
        """.toLines()
    }

}