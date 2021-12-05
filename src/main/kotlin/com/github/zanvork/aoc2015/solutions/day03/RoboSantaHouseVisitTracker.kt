package com.github.zanvork.aoc2015.solutions.day03

import com.github.zanvork.aoc2015.utils.InputUtil

object RoboSantaHouseVisitTracker {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = InputUtil.readLines("2015/day3input.txt").first()

        val housesVisited = findHousesVisited(input)

        println(housesVisited.size)
    }

    fun findHousesVisited(input: String): MutableSet<String> {
        var santaX = 0
        var santaY = 0
        var roboX = 0
        var roboY = 0
        var santaMoves = true
        val housesVisited = mutableSetOf<String>()
        housesVisited.add("x:0, y:0")
        input.toCharArray().forEach { char ->
            when (char) {
                '^' -> if (santaMoves) santaY++ else roboY++
                'v' -> if (santaMoves) santaY-- else roboY--
                '>' -> if (santaMoves) santaX++ else roboX++
                '<' -> if (santaMoves) santaX-- else roboX--
                else -> throw IllegalStateException("Unrecognised character $char")
            }
            if (santaMoves) {
                housesVisited.add("x:$santaX, y:$santaY")
            } else {
                housesVisited.add("x:$roboX, y:$roboY")
            }
            santaMoves = !santaMoves
        }
        return housesVisited
    }
}