class Problem10 : DailyProblemPair<Int, String> {

    sealed class Command(val addValue: Int)
    class OpNoop : Command(0) {
        override fun toString() = "noop"
    }
    class OpAddX1(amount: Int) : Command(0) {
        val name = "addx $amount"
        override fun toString() = name
    }
    class OpAddX2(amount: Int) : Command(amount) {
        val name = "addx $amount"
        override fun toString() = name
    }


    data class Sprite(var regX: Int, val width: Int) {
        fun intersects(x: Int) = (regX - 1 .. regX + 1).contains(x % width)
        override fun toString(): String {
            val max = width
            val max1 = max - 1
            val max2 = max - 2
            return "Sprite position: " + when (regX) {
                -1 -> "#" + ".".repeat(max1)
                0 -> "##" + ".".repeat(max2)
                in (1 .. (max2)) -> ".".repeat(regX - 1) + "###" + ".".repeat(max2 - regX)
                max1 -> ".".repeat(max2) + "##"
                max -> ".".repeat(max1) + "#"
                else -> ".".repeat(max)
            }
        }
    }

    class Raster(val width: Int, var line: String = "") {
        fun addChar(c: Char) { line += c }
        fun row1() = line.take(width)
        override fun toString(): String {
            return line.chunked(width).joinToString("\n")
        }
    }

    class CPU {
        val rasterWidth = 40
        val sprite = Sprite(1, rasterWidth)
        val raster = Raster(rasterWidth)

        fun cycleMsg(msg: String, cycleName: String) = msg + cycleName.padStart(15 - msg.length, ' ') + ": "
        fun executeCycle(cycle: Int, cmd: Command) {
//            val cycleName = (cycle + 1).toString()
//            if (cmd is OpAddX1)
//                println(cycleMsg("Start cycle", cycleName) + "begin executing " + cmd)
//            println(cycleMsg("During cycle", cycleName) + "CRT draws pixel in position ${cycle}")
            val pixel = if (sprite.intersects(cycle)) '#' else '.'
            raster.addChar(pixel)
//            println("Current CRT row: ${raster.row1()}")
            sprite.regX += cmd.addValue
//            if (cmd is OpAddX2) {
//                println(cycleMsg("End of cycle", cycleName) + "finish executing " + cmd + " (Register X is now ${sprite.regX})")
//                println(sprite)
//            }
//            println()
        }
        fun execute(ops: List<Command>) = ops.forEachIndexed { cycle, cmd ->  executeCycle(cycle, cmd) }
    }

    fun measureSignalStrength(cmds: List<Command>, measurementPoints: List<Int>): Int {
        var regX: Int = 1
        val ops = listOf(OpNoop()) + cmds
        return measurementPoints.zipWithNext().sumOf { cycleRange ->
            regX += ops.subList(cycleRange.first, cycleRange.second)
                .sumOf { it.addValue }
            regX * cycleRange.second
        }
    }

    val measurePoints = listOf(0, 20, 60, 100, 140, 180, 220)

    fun solvePart0(): Int {
        return measureSignalStrength(toCommands(data0), measurePoints)
    }

    override fun solvePart1(): Int {
        return measureSignalStrength(toCommands(data1), measurePoints)
    }

    fun solvePart0a(): String {
        val cpu = CPU().apply { execute(toCommands(data0)) }
        return cpu.raster.toString()
    }

    override fun solvePart2(): String {
        val cpu = CPU().apply { execute(toCommands(data1)) }
        return cpu.raster.toString()
    }

    fun toCommands(data: List<String>): List<Command> {
        return data.flatMap {
            val cmd = it.substring(0, 4)
            when (cmd) {
                "noop" -> listOf(OpNoop())
                "addx" -> {
                    val amount = it.substring(5).toInt()
                    listOf(OpAddX1(amount), OpAddX2(amount))
                }
                else -> throw Error("unknown command $cmd")
            }
        }
    }

    companion object {
        val data0 = """
            addx 15
            addx -11
            addx 6
            addx -3
            addx 5
            addx -1
            addx -8
            addx 13
            addx 4
            noop
            addx -1
            addx 5
            addx -1
            addx 5
            addx -1
            addx 5
            addx -1
            addx 5
            addx -1
            addx -35
            addx 1
            addx 24
            addx -19
            addx 1
            addx 16
            addx -11
            noop
            noop
            addx 21
            addx -15
            noop
            noop
            addx -3
            addx 9
            addx 1
            addx -3
            addx 8
            addx 1
            addx 5
            noop
            noop
            noop
            noop
            noop
            addx -36
            noop
            addx 1
            addx 7
            noop
            noop
            noop
            addx 2
            addx 6
            noop
            noop
            noop
            noop
            noop
            addx 1
            noop
            noop
            addx 7
            addx 1
            noop
            addx -13
            addx 13
            addx 7
            noop
            addx 1
            addx -33
            noop
            noop
            noop
            addx 2
            noop
            noop
            noop
            addx 8
            noop
            addx -1
            addx 2
            addx 1
            noop
            addx 17
            addx -9
            addx 1
            addx 1
            addx -3
            addx 11
            noop
            noop
            addx 1
            noop
            addx 1
            noop
            noop
            addx -13
            addx -19
            addx 1
            addx 3
            addx 26
            addx -30
            addx 12
            addx -1
            addx 3
            addx 1
            noop
            noop
            noop
            addx -9
            addx 18
            addx 1
            addx 2
            noop
            noop
            addx 9
            noop
            noop
            noop
            addx -1
            addx 2
            addx -37
            addx 1
            addx 3
            noop
            addx 15
            addx -21
            addx 22
            addx -6
            addx 1
            noop
            addx 2
            addx 1
            noop
            addx -10
            noop
            noop
            addx 20
            addx 1
            addx 2
            addx 2
            addx -6
            addx -11
            noop
            noop
            noop
        """.toLines()

        val data1 = """
noop
addx 22
addx -17
addx 1
addx 4
addx 17
addx -16
addx 4
addx 1
addx 21
addx -17
addx -10
noop
addx 17
addx -1
addx 5
addx -1
noop
addx 4
addx 1
noop
addx -37
addx 5
addx 27
addx -22
addx -2
addx 2
addx 5
addx 2
addx 5
noop
noop
addx -2
addx 5
addx 16
addx -11
addx -2
addx 2
addx 5
addx 2
addx -8
addx 9
addx -38
addx 5
addx 20
addx -16
addx 8
addx -5
addx 1
addx 4
noop
noop
addx 5
addx -2
noop
noop
addx 18
noop
addx -8
addx 2
addx 7
addx -2
noop
noop
noop
noop
noop
addx -35
noop
addx 32
addx -26
addx 12
addx -8
addx 3
noop
addx 2
addx 16
addx -24
addx 11
addx 3
addx -17
addx 17
addx 5
addx 2
addx -15
addx 22
addx 3
noop
addx -40
noop
addx 2
noop
addx 3
addx 13
addx -6
addx 10
addx -9
addx 2
addx 22
addx -15
addx 8
addx -7
addx 2
addx 5
addx 2
addx -32
addx 33
addx 2
addx 5
addx -39
addx -1
addx 3
addx 4
addx 1
addx 4
addx 21
addx -20
addx 2
addx 12
addx -4
noop
noop
noop
noop
noop
addx 4
noop
noop
noop
addx 6
addx -27
addx 31
noop
noop
noop
noop
noop
        """.toLines()
    }

}
