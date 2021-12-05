package com.github.zanvork.aoc2015.solutions.day03

import com.github.zanvork.aoc2015.utils.InputUtil

object SantaHouseVisitTracker {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = InputUtil.readLines("2015/day3input.txt").first()

        val housesVisited = findHousesVisited(input)

        println(housesVisited.size)
    }

    fun findHousesVisited(input: String): MutableSet<String> {
        var x = 0
        var y = 0
        val housesVisited = mutableSetOf<String>()
        housesVisited.add("x:$x, y:$y")
        input.toCharArray().forEach { char ->
            when (char) {
                '^' -> y++
                'v' -> y--
                '>' -> x++
                '<' -> x--
                else -> throw IllegalStateException("Unrecognised character $char")
            }
            housesVisited.add("x:$x, y:$y")
        }
        return housesVisited
    }
}