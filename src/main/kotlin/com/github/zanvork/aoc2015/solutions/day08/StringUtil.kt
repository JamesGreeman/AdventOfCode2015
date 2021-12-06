package com.github.zanvork.aoc2015.solutions.day08

import com.github.zanvork.aoc2015.utils.InputUtil


object StringUtil {

    @JvmStatic
    fun main(args: Array<String>) {
        val rawLines = InputUtil.readLines("2015/day08input.txt")

        val unescapedLines = rawLines.map { line ->
            line.removeQuotes().unescape()
        }

        val escapedLines = rawLines.map { line ->
            "\"${line.escape()}\""
        }

        val rawLen = rawLines.sumOf { it.length }
        val unescapedLength = unescapedLines.sumOf { it.length }
        val escapedLength = escapedLines.sumOf { it.length }

        println(rawLen - unescapedLength)

        println(escapedLength - rawLen)
    }

    private const val BACKSLASH_PLACEHOLDER = "{placeholder_for_backslash}"
    private const val DBL_QUOTE_PLACEHOLDER = "{placeholder_for_double_quote}"

    fun String.removeQuotes() : String {
        if (this.first() == '"' && this.last() == '"') {
            return this.substring(1, this.lastIndex)
        }
        return this
    }

    fun String.unescape() : String {
        return this.replace("\\\\", BACKSLASH_PLACEHOLDER)
                .replace("\\\"", DBL_QUOTE_PLACEHOLDER)
                .replace("\\\\x([0-9a-fA-F]{2})".toRegex()) { matchResult ->
                    matchResult.groupValues[1].toInt(16).toChar().toString()
                }
                .replace(BACKSLASH_PLACEHOLDER, "\\")
                .replace(DBL_QUOTE_PLACEHOLDER, "\"")
    }

    fun String.escape(): String {
        return this.replace("\\", BACKSLASH_PLACEHOLDER)
                .replace("\"", "\\\"")
                .replace(BACKSLASH_PLACEHOLDER, "\\\\")

    }


}