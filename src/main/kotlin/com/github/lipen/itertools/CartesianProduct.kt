package com.github.lipen.itertools

/**
 * Returns the cartesian product of iterables.
 * Resulting tuples (as `List<T>`) are emitted lazily in lexicographic sort order.
 */
fun <T> Collection<Iterable<T>>.cartesianProduct(): Sequence<List<T>> =
    when (size) {
        0 -> emptySequence()
        else -> fold(sequenceOf(listOf())) { acc, iterable ->
            acc.flatMap { list ->
                iterable.asSequence().map { element -> list + element }
            }
        }
    }

/**
 * Returns the cartesian product of iterables.
 * Resulting tuples (as `List<T>`) are emitted lazily in lexicographic sort order.
 */
fun <T> Iterable<Iterable<T>>.cartesianProduct(): Sequence<List<T>> = toList().cartesianProduct()

fun main() {
    val xs = listOf((1..4).toList(), "ABC".toList(), listOf("lol", "kek"))
    println("$xs.cartesianProduct():")
    for (p in xs.cartesianProduct()) {
        println("    $p")
    }
}
