package com.github.zanvork.aoc2015.solutions.day07

enum class LogicalOperation(val operation: (operandA: Short, operandB: Short?) -> Short) {

    LSHIFT({ a, b -> (a.toInt() shl b!!.toInt()).toShort() }),
    RSHIFT({ a, b -> (a.toInt() shr b!!.toInt()).toShort() }),
    OR({ a, b -> (a.toInt() or b!!.toInt()).toShort() }),
    AND({ a, b -> (a.toInt() and b!!.toInt()).toShort() }),
    NOT({ a, _ -> (a.toInt().inv()).toShort() });

    fun runOperation(operandA: Short, operandB: Short?): Short = operation(operandA, operandB)
}