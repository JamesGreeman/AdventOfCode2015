package com.github.zanvork.aoc2015.solutions.day10

object NumberCounter {

    fun transform(input: String): String {
        var repeat = input.first()
        var times = 1
        var newString = ""
        for (char in input.drop(1).toCharArray()) {
            if (repeat != char) {
                newString += "$times$repeat"
                repeat = char
                times = 1
            } else {
                times++
            }
        }
        newString += "$times$repeat"
        return newString
    }

    @JvmStatic
    fun main(args: Array<String>) {
        var input = "1113122113"
        for (i in 1..50) {
            input = transform(input)
            println(input.length)
        }
        println(input.length)
    }



}