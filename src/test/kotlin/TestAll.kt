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

}