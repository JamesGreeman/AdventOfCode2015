package com.github.zanvork.aoc2015.solutions.day07

import com.github.zanvork.aoc2015.utils.InputUtil

class CircuitInstructionRunner(private val wireDefinitions: Map<String, String>,
                               overrideValues: Map<String, Short> = emptyMap()) {

    private val wireValues = mutableMapOf<String, Short>()

    init {
        wireValues.putAll(overrideValues)
    }

    fun resolveWireValue(wireName: String): Short {
        val wireDefinition = wireDefinitions.getValue(wireName)
        return wireValues.getOrPut(wireName) {
            resolveIfAsignment(wireDefinition)
                    ?: resolveIfNot(wireDefinition)
                    ?: resolveIfOtherOperation(wireDefinition)
                    ?: throw IllegalArgumentException("Wire definition did not match regexes: '$wireDefinition'")
        }

    }

    private fun resolveIfOtherOperation(wireDefinition: String): Short? {
        return OTHER_OP_REGEX.find(wireDefinition)?.let { matchResult ->
            val inputA = resolveInput(matchResult.groupValues[1])
            val inputB = resolveInput(matchResult.groupValues[3])
            val operation = LogicalOperation.valueOf(matchResult.groupValues[2])
            operation.runOperation(inputA, inputB)
        }
    }

    private fun resolveIfNot(wireDefinition: String): Short? {
        return NOT_REGEX.find(wireDefinition)?.let { matchResult ->
            LogicalOperation.NOT.runOperation(resolveInput(matchResult.groupValues[1]), null)
        }
    }

    private fun resolveIfAsignment(wireDefinition: String): Short? {
        return ASSIGNMENT_REGEX.find(wireDefinition)?.let { matchResult ->
            resolveInput(matchResult.groupValues[1])
        }
    }

    private fun resolveInput(wireInput: String): Short {
        return wireInput.toShortOrNull() ?: resolveWireValue(wireInput)
    }

    companion object {

        private val ASSIGNMENT_REGEX = Regex("^([a-z]+|\\d+)$")
        private val NOT_REGEX = Regex("^NOT ([a-z]+|\\d+)$")
        private val OTHER_OP_REGEX = Regex("^([a-z]+|\\d+) (LSHIFT|RSHIFT|AND|OR) ([a-z]+|\\d+)$")

        @JvmStatic
        fun main(args: Array<String>) {
            val wireDefinitionRegex = Regex("^(.+) -> ([a-z]+)$")

            fun getWireDefinition(instruction: String): Pair<String, String> {
                return wireDefinitionRegex.find(instruction)?.let { matchResult ->
                    matchResult.groupValues[2] to matchResult.groupValues[1]
                } ?: throw IllegalArgumentException("Could not parse wire definition")
            }

            val instructions = InputUtil.readLines("2015/day07input.txt")
                    .associate { line -> getWireDefinition(line) }

            val instructionRunner = CircuitInstructionRunner(instructions)

            val aValue = instructionRunner.resolveWireValue("a")
            println("Initial A: $aValue")

            val newRunner = CircuitInstructionRunner(instructions, mapOf("b" to aValue))

            val newA = newRunner.resolveWireValue("a")

            println("New A: $newA")
        }
    }

}