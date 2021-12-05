package com.github.zanvork.aoc2015.solutions.day02

import com.github.zanvork.aoc2015.utils.InputUtil

object RibbonCalculator {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = InputUtil.readLines("2015/day2input.txt")
                .map { line ->
                    line.split("x")
                            .map { dimension -> dimension.toInt() }
                            .sorted()
                }

        val totalRibbonNeeded = input.sumOf { (a, b, c) ->
            val smallestPerimeter = 2 * a + 2 * b
            val volume = a * b * c
            smallestPerimeter + volume
        }

        println(totalRibbonNeeded)

    }
}