import kotlin.math.abs

class Problem15 : DailyProblemPair<Int, Long> {

    class IntRangeSet(val ranges: Set<IntRange> = setOf()) {
        fun union(range: IntRange): IntRangeSet {
            val hitMiss = ranges.partition { it.intersects(range) }
            val ranges = mutableSetOf<IntRange>().apply {
                add(hitMiss.first.fold(range) { acc, intRange -> acc.envelop(intRange) })
                addAll(hitMiss.second)
            }
            return IntRangeSet(ranges)
        }

        fun invert(boundary: IntRange): IntRangeSet {
            val pts = listOf(boundary.first) +
                    ranges.sortedBy { it.first }.flatMap { r -> listOf(r.first - 1, r.last + 1) } +
                    listOf(boundary.last)
            return IntRangeSet(pts.chunked(2).map { IntRange(it[0], it[1]) }.toSet())
        }

        fun size() = ranges.sumOf { it.size() }
    }

    data class Pt(val x: Int, val y: Int)
    data class SensorBeacon(val sensor: Pt, val beacon: Pt) {
        private val distance: Int by lazy { abs(sensor.x - beacon.x) + abs(sensor.y - beacon.y) }
        fun sliceAtY(y: Int) = (distance - abs(sensor.y - y)).let { delta ->
            if (delta < 0) null else IntRange(sensor.x - delta, sensor.x + delta)
        }
    }

    fun notAllowedXPositions(sensors: List<SensorBeacon>, yIntercept: Int): IntRangeSet {
        return sensors.mapNotNull { it.sliceAtY(yIntercept) }.fold(IntRangeSet()) { acc, intRange -> acc.union(intRange) }
    }

    fun allowedXPositions(boundaryRange: IntRange, sensors: List<SensorBeacon>, yIntercept: Int): IntRangeSet {
        return notAllowedXPositions(sensors, yIntercept).invert(boundaryRange)
    }

    fun solveSlice(sensors: List<SensorBeacon>, yIntercept: Int): Int {
        val beaconCountAtSlice = sensors.map { it.beacon }.filter { it.y == yIntercept }.toSet().count()
        return notAllowedXPositions(sensors, yIntercept).size() - beaconCountAtSlice
    }

    fun solveSearch(sensors: List<SensorBeacon>, inRange: IntRange): Long {
        inRange.forEach { y ->
            allowedXPositions(inRange, sensors, y).ranges.firstOrNull { 1 == it.size() }?.let {
                return it.first * 4000000L + y
            }
        }
        throw Error("No beacon spot found")
    }

    fun solvePart0(): Int {
        return solveSlice(parseToSensorBeacons(data0), 10)
    }

    override fun solvePart1(): Int {
        return solveSlice(parseToSensorBeacons(data1), 2000000)
    }

    fun solvePart0a(): Long {
        return solveSearch(parseToSensorBeacons(data0), 0..20)
    }

    override fun solvePart2(): Long {
        return solveSearch(parseToSensorBeacons(data1), 0..4000000)
    }

    fun parseToSensorBeacons(data: List<String>): List<SensorBeacon> {
        val regex = "Sensor at x=([\\d-]+), y=([\\d-]+): closest beacon is at x=([\\d-]+), y=([\\d-]+)".toRegex()
        return data.map {
            val values = regex.matchEntire(it)?.groupValues ?: throw Error("could not parse line ${it}")
            val sensor = Pt(values[1].toInt(), values[2].toInt())
            val beacon = Pt(values[3].toInt(), values[4].toInt())
            SensorBeacon(sensor, beacon)
        }
    }

    companion object {
        val data0 = """
Sensor at x=2, y=18: closest beacon is at x=-2, y=15
Sensor at x=9, y=16: closest beacon is at x=10, y=16
Sensor at x=13, y=2: closest beacon is at x=15, y=3
Sensor at x=12, y=14: closest beacon is at x=10, y=16
Sensor at x=10, y=20: closest beacon is at x=10, y=16
Sensor at x=14, y=17: closest beacon is at x=10, y=16
Sensor at x=8, y=7: closest beacon is at x=2, y=10
Sensor at x=2, y=0: closest beacon is at x=2, y=10
Sensor at x=0, y=11: closest beacon is at x=2, y=10
Sensor at x=20, y=14: closest beacon is at x=25, y=17
Sensor at x=17, y=20: closest beacon is at x=21, y=22
Sensor at x=16, y=7: closest beacon is at x=15, y=3
Sensor at x=14, y=3: closest beacon is at x=15, y=3
Sensor at x=20, y=1: closest beacon is at x=15, y=3
        """.toLines()

        val data1 = """
Sensor at x=3988693, y=3986119: closest beacon is at x=3979063, y=3856315
Sensor at x=1129181, y=241785: closest beacon is at x=1973630, y=-98830
Sensor at x=2761889, y=2453622: closest beacon is at x=2803715, y=2643139
Sensor at x=3805407, y=3099635: closest beacon is at x=3744251, y=2600851
Sensor at x=3835655, y=3999745: closest beacon is at x=3979063, y=3856315
Sensor at x=3468377, y=3661078: closest beacon is at x=3979063, y=3856315
Sensor at x=1807102, y=3829998: closest beacon is at x=2445544, y=3467698
Sensor at x=2774374, y=551040: closest beacon is at x=1973630, y=-98830
Sensor at x=2004588, y=2577348: closest beacon is at x=2803715, y=2643139
Sensor at x=2949255, y=3611925: closest beacon is at x=2445544, y=3467698
Sensor at x=2645982, y=3991988: closest beacon is at x=2445544, y=3467698
Sensor at x=3444780, y=2880445: closest beacon is at x=3744251, y=2600851
Sensor at x=3926452, y=2231046: closest beacon is at x=3744251, y=2600851
Sensor at x=3052632, y=2882560: closest beacon is at x=2803715, y=2643139
Sensor at x=3994992, y=2720288: closest beacon is at x=3744251, y=2600851
Sensor at x=3368581, y=1443706: closest beacon is at x=3744251, y=2600851
Sensor at x=2161363, y=1856161: closest beacon is at x=1163688, y=2000000
Sensor at x=3994153, y=3414445: closest beacon is at x=3979063, y=3856315
Sensor at x=2541906, y=2965730: closest beacon is at x=2803715, y=2643139
Sensor at x=600169, y=3131140: closest beacon is at x=1163688, y=2000000
Sensor at x=163617, y=1082438: closest beacon is at x=1163688, y=2000000
Sensor at x=3728368, y=140105: closest beacon is at x=3732654, y=-724773
Sensor at x=1187681, y=2105247: closest beacon is at x=1163688, y=2000000
Sensor at x=2327144, y=3342616: closest beacon is at x=2445544, y=3467698
        """.toLines()
    }

}