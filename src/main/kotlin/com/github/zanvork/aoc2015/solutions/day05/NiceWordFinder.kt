package com.github.zanvork.aoc2015.solutions.day05

import com.github.zanvork.aoc2015.utils.InputUtil

object NiceWordFinder {

    private val VOWELS = listOf('a', 'e', 'i', 'o', 'u')
    private val BAD_STRINGS = listOf("ab", "cd", "pq", "xy")


    @JvmStatic
    fun main(args: Array<String>) {
        val input = InputUtil.readLines("2015/day5input.txt")
        val niceStrings = input
                .filter { string -> string.toCharArray().filter { char -> char in VOWELS }.size >= 3 }
                .also { println(it.size) }
                .filter { string ->
                    val chars = string.toCharArray()
                    (0..chars.size -2).any { i ->
                        chars[i] == chars[i+1]
                    }
                }
                .also { println(it.size) }
                .filter { string -> BAD_STRINGS.none { badString -> string.contains(badString)} }
                .also { println(it.size) }

        println(niceStrings.size)
    }
}