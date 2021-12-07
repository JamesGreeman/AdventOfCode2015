package com.github.zanvork.aoc2015.solutions.day11

import com.github.zanvork.aoc2015.solutions.day05.NiceWordFinder2

object PasswordGenerator {

    fun incrementString(input: String) : String {
        var index = input.length -1
        var newChar: Char
        val asArray = input.toCharArray()
        do {
            newChar = incrementChar(asArray[index])
            asArray[index] = newChar
            index--
        } while (newChar == 'a')
        return String(asArray)
    }

    fun incrementChar(char: Char): Char {
        if (char < 'a' || char > 'z') {
            throw IllegalArgumentException("Char must be a lower case letter")
        }
        return when (char) {
            'h' -> 'j' //Exclude i
            'k' -> 'm' //Exclude l
            'n' -> 'p' //Exclude o
            'z' -> 'a' //Loop Around
            else -> char.inc()
        }
    }

    fun isValid(input: String) :Boolean {
         return containsThreeSequentialCharacters(input) && countUniqueRepeatingPairs(input) >= 2
    }

    fun containsThreeSequentialCharacters(input: String): Boolean {
        val asArray = input.toCharArray()
        for (i in 0..input.length - 3) {
            if (asArray[i].inc() == asArray[i+1] && asArray[i+1].inc() == asArray[i+2]) {
                return true
            }
        }
        return false

    }

    private fun countUniqueRepeatingPairs(input: String): Int {
        val pairsFound = mutableSetOf<Char>()
        val asArray = input.toCharArray()
        for (i in 0..input.length - 2) {
            if (asArray[i] == asArray[i+1]) {
                pairsFound.add(asArray[i])
            }
        }
        return pairsFound.size
    }

    fun findNext(input: String): String {
        var input1 = input
        do {
            input1 = incrementString(input1)
        }
        while (!isValid(input1))
        return input1
    }
    @JvmStatic
    fun main(args: Array<String>) {
        var input = "cqjxjnds"
        input = findNext(input)
        println(input)
        input = findNext(input)
        println(input)
    }



}