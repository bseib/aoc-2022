
fun Iterable<Int>.product(): Int {
    var product: Int = 1
    for (element in this) {
        product *= element
    }
    return product
}

fun Iterable<Long>.product(): Long {
    var product: Long = 1
    for (element in this) {
        product *= element
    }
    return product
}
