package com.github.lipen.itertools

/**
 * Returns all [r]-length subsequences of elements from the iterable
 * allowing individual elements to be repeated more than once.
 * Combinations are emitted lazily in lexicographic sort order.
 */
fun <T> Iterable<T>.combinationsWithReplacement(r: Int): Sequence<List<T>> = when {
    r < 0 -> error("r must be non-negative")
    r == 0 -> sequenceOf(emptyList())
    else -> sequence {
        val pool = this@combinationsWithReplacement.toList()
        val n = pool.size
        if (n == 0) return@sequence
        val indices = IntArray(r)

        yield(indices.map { pool[it] })

        while (indices[0] != n - 1) {
            var i = r - 1
            while (indices[i] == n - 1) i--

            indices[i]++
            for (j in (i + 1) until r) {
                indices[j] = indices[i]
            }

            yield(indices.map { pool[it] })
        }
    }
}

fun main() {
    val xs = (1..4).toList()
    val combo3of4 = xs.combinationsWithReplacement(3).toList()
    println("combinationsWithReplacement($xs, r = 3) = $combo3of4\n")
    check(
        combo3of4 == listOf(
            listOf(1, 1, 1),
            listOf(1, 1, 2),
            listOf(1, 1, 3),
            listOf(1, 1, 4),
            listOf(1, 2, 2),
            listOf(1, 2, 3),
            listOf(1, 2, 4),
            listOf(1, 3, 3),
            listOf(1, 3, 4),
            listOf(1, 4, 4),
            listOf(2, 2, 2),
            listOf(2, 2, 3),
            listOf(2, 2, 4),
            listOf(2, 3, 3),
            listOf(2, 3, 4),
            listOf(2, 4, 4),
            listOf(3, 3, 3),
            listOf(3, 3, 4),
            listOf(3, 4, 4),
            listOf(4, 4, 4)
        )
    )
}
