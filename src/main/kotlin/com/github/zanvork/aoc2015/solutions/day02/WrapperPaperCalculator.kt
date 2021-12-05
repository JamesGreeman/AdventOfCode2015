package com.github.zanvork.aoc2015.solutions.day02

import com.github.zanvork.aoc2015.utils.InputUtil

object WrapperPaperCalculator {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = InputUtil.readLines("2015/day2input.txt")
                .map { line ->
                    line.split("x")
                            .map { dimension -> dimension.toInt() }
                            .sorted()
                }

        val totalArea = input.sumOf { (a, b, c) ->
            val areaA = a*b
            val areaB = a*c
            val areaC = b*c
            areaA * 3 + areaB * 2 + areaC * 2
        }

        println(totalArea)

    }
}