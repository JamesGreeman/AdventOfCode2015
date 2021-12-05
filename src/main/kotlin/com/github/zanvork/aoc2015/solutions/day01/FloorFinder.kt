package com.github.zanvork.aoc2015.solutions.day01

import com.github.zanvork.aoc2015.utils.InputUtil

object FloorFinder {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = InputUtil.readLines("2015/day1input.txt").first()

        val floors = input.toCharArray()
                .map { char -> when(char) {
                    '(' -> 1
                    ')' -> -1
                    else -> throw IllegalStateException("Unrecognised character $char")
                } }
                .sum()

        println(floors)
    }
}