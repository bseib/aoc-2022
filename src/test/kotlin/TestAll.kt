import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class TestAll {

    @Test
    fun day01() {
        Problem1().apply {
            assertEquals(69177, solvePart1())
            assertEquals(207456, solvePart2())
        }
    }

    @Test
    fun day02() {
        Problem2().apply {
            assertEquals(15, solvePart0())
            assertEquals(12586, solvePart1())
            assertEquals(13193, solvePart2())
        }
    }

    @Test
    fun day03() {
        Problem3().apply {
            assertEquals(157, solvePart0())
            assertEquals(7848, solvePart1())
            assertEquals(2616, solvePart2())
        }
    }

    @Test
    fun day04() {
        Problem4().apply {
            assertEquals(2, solvePart0())
            assertEquals(582, solvePart1())
            assertEquals(893, solvePart2())
        }
    }

    @Test
    fun day05() {
        Problem5().apply {
            assertEquals("CMZ", solvePart0())
            assertEquals("VJSFHWGFT", solvePart1())
            assertEquals("LCTQFBVZV", solvePart2())
        }
    }

    @Test
    fun day06() {
        Problem6().apply {
            assertEquals(5, solvePart0a())
            assertEquals(6, solvePart0b())
            assertEquals(10, solvePart0c())
            assertEquals(11, solvePart0d())
            assertEquals(1093, solvePart1())
            assertEquals(3534, solvePart2())
        }
    }

    @Test
    fun day07() {
        Problem7().apply {
            assertEquals(95437, solvePart0())
            assertEquals(1783610, solvePart1())
            assertEquals(4370655, solvePart2())
        }
    }

    @Test
    fun day08() {
        Problem8().apply {
            assertEquals(21, solvePart0())
            assertEquals(1820, solvePart1())
            assertEquals(385112, solvePart2())
        }
    }

    @Test
    fun day09() {
        Problem9().apply {
            assertEquals(13, solvePart0())
            assertEquals(6236, solvePart1())
            assertEquals(36, solvePart0a())
            assertEquals(2449, solvePart2())
        }
    }

    @Test
    fun day10() {
        Problem10().apply {
            assertEquals(13140, solvePart0())
            assertEquals(13860, solvePart1())
            val example0a = """
                ##..##..##..##..##..##..##..##..##..##..
                ###...###...###...###...###...###...###.
                ####....####....####....####....####....
                #####.....#####.....#####.....#####.....
                ######......######......######......####
                #######.......#######.......#######.....
            """.trimIndent()
            assertEquals(example0a, solvePart0a())
            val part2 = """
                ###..####.#..#.####..##....##..##..###..
                #..#....#.#..#.#....#..#....#.#..#.#..#.
                #..#...#..####.###..#.......#.#....###..
                ###...#...#..#.#....#.##....#.#....#..#.
                #.#..#....#..#.#....#..#.#..#.#..#.#..#.
                #..#.####.#..#.#.....###..##...##..###..
            """.trimIndent() // RZHFGJCB
            assertEquals(part2, solvePart2())
        }
    }

    @Test
    fun day11() {
        Problem11().apply {
            assertEquals(10605, solvePart0())
            assertEquals(50830, solvePart1())
            assertEquals(2713310158, solvePart0a())
            assertEquals(14399640002, solvePart2())
        }
    }

    @Test
    fun day12() {
        Problem12().apply {
            assertEquals(31, solvePart0())
            assertEquals(449, solvePart1())
            assertEquals(443, solvePart2())
        }
    }

    @Test
    fun day13() {
        Problem13().apply {
            assertEquals(13, solvePart0())
            assertEquals(5717, solvePart1())
            assertEquals(140, solvePart0a())
            assertEquals(25935, solvePart2())
        }
    }

    @Test
    fun day14() {
        Problem14().apply {
            assertEquals(24, solvePart0())
            assertEquals(674, solvePart1())
            assertEquals(0, solvePart2())
        }
    }

}