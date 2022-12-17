
fun toIntRange(a: Int, b: Int) = if (a < b ) IntRange(a, b) else IntRange(b, a)

fun IntRange.size() = this.last - this.first + 1

fun IntRange.intersects(other: IntRange) = this.contains(other.first) ||
        this.contains(other.last) ||
        other.contains(this.first)

// If we know the two already "touch", envelop() will do the same as a "union".
fun IntRange.envelop(other: IntRange) = IntRange(minOf(this.first, other.first), maxOf(this.last, other.last))
fun IntRange.clip(bound: IntRange) = IntRange(maxOf(this.first, bound.first), minOf(this.last, bound.last))
