package com.github.zanvork.aoc2015.utils

object InputUtil {

    fun readLines(inputFileName: String): Iterable<String> {
        return InputUtil::class.java.classLoader.getResource(inputFileName).readText()
                .split("\n")
    }
}