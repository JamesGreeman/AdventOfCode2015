package com.github.zanvork.aoc2015.solutions.day01

import com.github.zanvork.aoc2015.utils.InputUtil

object BasementFinder {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = InputUtil.readLines("2015/day1input.txt").first()

        val position = findFirstBasementPosition(input.toCharArray())
        println(position)

    }

    private fun findFirstBasementPosition(input: CharArray): Int {

        var position = 1
        var floor = 0
        input.forEach { char ->
            when(char) {
                '(' -> floor++
                ')' -> floor--
                else -> throw IllegalStateException("Unrecognised character $char")
            }
            if (floor == -1) {
                return position
            }
            position++
        }
        throw IllegalStateException("Never reached basement.")
    }


}