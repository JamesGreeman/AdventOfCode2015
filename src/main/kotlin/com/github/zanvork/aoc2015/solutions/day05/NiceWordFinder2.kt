package com.github.zanvork.aoc2015.solutions.day05

import com.github.zanvork.aoc2015.utils.InputUtil

object NiceWordFinder2 {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = InputUtil.readLines("2015/day5input.txt")
        val niceStrings = input
                .filter { string ->
                    val chars = string.toCharArray()
                    (0..chars.size - 3).any { i ->
                        chars[i] == chars[i + 2]
                    }
                }
                .filter { string -> containsRepeatingCharacters(string) }

        println(niceStrings.size)
    }

    private fun containsRepeatingCharacters(input: String): Boolean {
        val chunkedSequences = input.chunked(2)
                .mapIndexed { i, letters -> letters to i * 2 }.plus(
                        input.drop(1).chunked(2)
                                .mapIndexed { i, letters -> letters to i * 2 + 1 })
                .filter { (string, _) -> string.length == 2 }
                .sortedBy { (_, startPos) -> startPos }

        val groupedWithStartingPosition = chunkedSequences
                .groupBy { (string, _) -> string }
                .mapValues { (_, value) -> value.map { (_, startPos) -> startPos } }
                .filterValues { startingPositions -> startingPositions.size > 1 }


        return groupedWithStartingPosition.any { (_, startingPositions) ->
            return startingPositions.first() != startingPositions.last() -1
        }
    }
}