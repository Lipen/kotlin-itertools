package com.github.lipen.itertools

/**
 * Returns all subsets of the iterable.
 */
fun <T> Iterable<T>.powerset(): Sequence<List<T>> = sequence {
    val pool = this@powerset.toList()
    val n = pool.size
    for (i in 0 until 2.pow(n)) {
        val combo = (0 until n)
            .filter { j -> i.nthBit(j) == 1 }
            .map { j -> pool[j] }
        yield(combo)
    }
}

private fun Int.pow(exp: Int): Int {
    return toBigInteger().pow(exp).toInt()
}

private fun Int.nthBit(n: Int): Int {
    return (this shr n) % 2
}

fun main() {
    val xs = (1..4).toList()
    println("$xs.powerset():")
    for (subset in xs.powerset()) {
        println("    $subset")
    }
}
