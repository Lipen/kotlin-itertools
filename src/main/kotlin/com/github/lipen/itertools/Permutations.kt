package com.github.lipen.itertools

/**
 * Returns all full-length permutations of elements from the iterable.
 * Permutations are emitted lazily in lexicographic sort order.
 *
 * Implementation of Knuth's Algorithm L (see fasc2b).
 */
fun <T> Iterable<T>.permutations(): Sequence<List<T>> = sequence {
    val pool = this@permutations.toList()
    val n = pool.size
    val indices = IntArray(n) { it }

    while (true) {
        // [Visit]
        yield(indices.map { pool[it] })

        // [Find j]
        var j = n - 2
        while (j >= 0 && indices[j] >= indices[j + 1]) j--
        if (j < 0) break // The end

        // [Increase a_j]
        var l = n - 1
        while (indices[j] >= indices[l]) l--
        println("swap ${j + 1} and ${l + 1}")
        indices.swap(j, l)

        // [Reverse a_{j+1}..a_n]
        var k = j + 1
        l = n - 1
        while (k < l) {
            println("swap ${k + 1} and ${l + 1}")
            indices.swap(k, l)
            k++
            l--
        }
    }
}

private fun IntArray.swap(i: Int, j: Int) {
    val temp = this[i]
    this[i] = this[j]
    this[j] = temp
}

fun main() {
    val xs = (1..4).toList()
    println("$xs.permutations():")
    for (perm in xs.permutations()) {
        println("    $perm\n")
    }
}
