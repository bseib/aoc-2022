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
            assertEquals(0, solvePart1())
            assertEquals(0, solvePart2())
        }
    }

}