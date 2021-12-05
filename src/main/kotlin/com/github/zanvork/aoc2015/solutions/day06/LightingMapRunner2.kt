package com.github.zanvork.aoc2015.solutions.day06

import com.github.zanvork.aoc2015.utils.InputUtil

object LightingMapRunner2 {

    private val INSTRUCTION_REGEX = Regex("^(turn on|turn off|toggle) (\\d+),(\\d+) through (\\d+),(\\d+)$")

    @JvmStatic
    fun main(args: Array<String>) {
        val grid = initialiseGrid()

        InputUtil.readLines("2015/day06input.txt")
                .map { instruction -> parseInstruction(instruction) }
                .forEach { instruction -> runInstruction(grid, instruction) }


        grid.forEach { println(it) }

        println(grid.flatten().sum())
    }

    private fun runInstruction(grid: List<MutableList<Int>>, instruction: Instruction) {
        val (start, end) = instruction.getNormalisedPoints()

        (start.x .. end.x).forEach { x ->
            (start.y .. end.y).forEach { y ->
                when(instruction.instructionType) {
                    InstructionType.ON -> grid[x][y] += 1
                    InstructionType.OFF -> grid[x][y] = maxOf(0, grid[x][y] - 1)
                    InstructionType.TOGGLE -> grid[x][y] +=2
                }
            }
        }
    }

    private fun initialiseGrid(): List<MutableList<Int>> {
        return List(1000) {
            MutableList(1000) {
                0
            }
        }
    }

    fun parseInstruction(instruction: String) : Instruction {
        val match = INSTRUCTION_REGEX.find(instruction) ?:
                throw IllegalArgumentException("Cannot match instruction $instruction")

        println(instruction)

        return Instruction(
                getInstructionType(match.groupValues[1]),
                Point(match.groupValues[2].toInt(),match.groupValues[3].toInt()),
                Point(match.groupValues[4].toInt(),match.groupValues[5].toInt())
        ).also { println(it) }
    }

    fun getInstructionType(instructionType: String): InstructionType {
        return when (instructionType) {
            "turn on" -> InstructionType.ON
            "turn off" -> InstructionType.OFF
            "toggle" -> InstructionType.TOGGLE
            else -> throw IllegalArgumentException("The instruction type did not match an expected value '$instructionType'")
        }
    }


    data class Instruction(val instructionType: InstructionType, val startPoint: Point, val endPoint: Point) {
        fun getNormalisedPoints(): Pair<Point, Point> {
            val minX = minOf(startPoint.x, endPoint.x)
            val minY = minOf(startPoint.y, endPoint.y)
            val maxX = maxOf(startPoint.x, endPoint.x)
            val maxY = maxOf(startPoint.y, endPoint.y)

            return Point(minX, minY) to Point(maxX, maxY)
        }
    }

    enum class InstructionType {
        ON,
        OFF,
        TOGGLE
    }

    data class Point(val x: Int, val y: Int)
}