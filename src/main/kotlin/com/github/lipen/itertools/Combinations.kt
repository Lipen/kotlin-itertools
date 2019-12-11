package com.github.lipen.itertools

/**
 * Returns all [r]-length subsequences of elements from the iterable.
 * Combinations are emitted lazily in lexicographic sort order.
 */
fun <T> Iterable<T>.combinations(r: Int): Sequence<List<T>> = when {
    r < 0 -> error("r must be non-negative")
    r == 0 -> sequenceOf(emptyList())
    else -> sequence {
        val pool = this@combinations.toList()
        val n = pool.size
        if (r > n) return@sequence
        yield(pool.take(r))
        val indices = IntArray(r) { it }

        while (indices[0] != n - r) {
            var i = r - 1
            while (indices[i] == i + n - r) i--

            indices[i]++
            for (j in (i + 1) until r) {
                indices[j] = indices[j - 1] + 1
            }

            yield(indices.map { pool[it] })
        }
    }
}

fun main() {
    val xs = (1..4).toList()
    val combo3of4 = xs.combinations(3).toList()
    println("combinations($xs, r = 3) = $combo3of4\n")
    check(
        combo3of4 == listOf(
            listOf(1, 2, 3),
            listOf(1, 2, 4),
            listOf(1, 3, 4),
            listOf(2, 3, 4)
        )
    )

    val ys = (1..5).toList()
    val combo3of5 = ys.combinations(3).toList()
    println("combinations($ys, r = 3) = $combo3of5\n")
    check(
        combo3of5 == listOf(
            listOf(1, 2, 3),
            listOf(1, 2, 4),
            listOf(1, 2, 5),
            listOf(1, 3, 4),
            listOf(1, 3, 5),
            listOf(1, 4, 5),
            listOf(2, 3, 4),
            listOf(2, 3, 5),
            listOf(2, 4, 5),
            listOf(3, 4, 5)
        )
    )
}
