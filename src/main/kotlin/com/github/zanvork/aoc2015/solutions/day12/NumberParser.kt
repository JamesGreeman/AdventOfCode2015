package com.github.zanvork.aoc2015.solutions.day12

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.zanvork.aoc2015.utils.InputUtil

object NumberParser {
    private val NUMBER_REGEX = "(-?\\d+)".toRegex()

    private val OBJECT_MAPPER = jacksonObjectMapper()

    fun parseNumbers(string: String): List<Int> {
        return NUMBER_REGEX.findAll(string)
                .map { matcher -> matcher.groupValues[1].toInt() }
                .toList()
    }

    fun parseAllNumbers(lines: Iterable<String>) : List<Int> {
        return lines.flatMap { line -> parseNumbers(line) }
    }

    fun parse(node: JsonNode): List<Int> {
        when {
            node.isNumber -> return listOf(node.asInt())
            node.isTextual -> return emptyList()
            node.isArray -> return node.flatMap { child -> parse(child) }
            node.isObject -> {
                if (node.fields().asSequence().any { (_, entry) -> entry.isTextual && entry.textValue() == "red" }) {
                    return emptyList()
                }
                return node.flatMap { child -> parse(child) }
            }
            else -> throw IllegalArgumentException("Node is of unknown type ${node.nodeType} - $node")
        }

    }

    @JvmStatic
    fun main(args: Array<String>) {
        val allNumbers = parseAllNumbers(InputUtil.readLines("2015/day12input.txt"))
        println(allNumbers)
        println(allNumbers.sum())

        val node = OBJECT_MAPPER.readTree(InputUtil.readLines("2015/day12input.txt").first())

        val allNonRedNumbers = parse(node)
        println(allNonRedNumbers)
        println(allNonRedNumbers.sum())
    }
}