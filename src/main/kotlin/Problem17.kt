class Problem17 : DailyProblem<Int> {

    class Rock(image: String) {
        val pixels: Array<CharArray>
        val width: Int
        val height: Int

        init {
            val rows = image.split("\n")
            pixels = Array(rows.size) { i -> rows[i].toCharArray() }
            width = pixels[0].size
            height = pixels.size
        }

        override fun toString() = pixels.map { it.joinToString("") }.joinToString("\n")
    }

    val rocks = listOf(
        Rock(
            """
            ####
        """.trimIndent()
        ),
        Rock(
            """
            .#.
            ###
            .#.
        """.trimIndent()
        ),
        Rock(
            """
            ..#
            ..#
            ###
        """.trimIndent()
        ),
        Rock(
            """
            #
            #
            #
            #
        """.trimIndent()
        ),
        Rock(
            """
            ##
            ##
        """.trimIndent()
        ),
    )

    data class Pt(val x: Int, val y: Int)

    class Field() {
        val rows = ArrayDeque<CharArray>()

        init {
            rows.addFirst("+-------+".toCharArray())
        }

        fun addThreeEmptyRows() = addEmptyRows(3)
        fun addEmptyRows(count: Int) = repeat(count) { rows.addFirst("|.......|".toCharArray()) }

        fun dropRock(r: Rock, jets: Sequence<Char>) {
            addThreeEmptyRows()
            addEmptyRows(r.height)
            val pos = Pt(2, 0)

            val jet = jets.take(1)


        }

    }

    fun rockFeeder() = generateSequence(0) { (it + 1) % rocks.size }.map { rocks[it] }
    fun jetFeeder(pattern: CharArray) = generateSequence(0) { (it + 1) % pattern.size }.map { pattern[it] }

    fun solvePart0(): Int {
        val field = Field()
        val jet = jetFeeder(jetPattern0)
        rockFeeder().take(1).forEach { r ->
            field.dropRock(r, jet)

        }
        return 0
    }

    override fun solvePart1(): Int {
        return 0
    }

    override fun solvePart2(): Int {
        return 0
    }

    companion object {
        val jetPattern0 = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>".toCharArray()

    }

}