package com.github.zanvork.aoc2015.solutions.day13

import com.github.zanvork.aoc2015.utils.InputUtil

object SeatingFinder {

    private val HAPPINESS_REGEX = "^([a-zA-Z]+) would (gain|lose) (\\d+) happiness units by sitting next to ([a-zA-Z]+).$".toRegex()

    fun loadChanges(): Map<String, Map<String, Int>> {
        return InputUtil.readLines("2015/day13input.txt")
                .map { line ->
                    HAPPINESS_REGEX.find(line)?.let { match ->
                        val name1 = match.groupValues[1]
                        val name2 = match.groupValues[4]
                        val change = if (match.groupValues[2] == "lose") {
                            -match.groupValues[3].toInt()
                        } else {
                            match.groupValues[3].toInt()
                        }
                        name1 to (name2 to change)
                    } ?: throw IllegalArgumentException("Could not match regex to $line")
                }.groupBy { (name, _) -> name }
                .mapValues { (_, values) ->
                    values.associate { (_, pair) -> pair }
                }
    }

    fun <A> getAllOrders(list: List<A>): List<List<A>> {
        if (list.size == 1) {
            return listOf(list)
        }
        return list.flatMap { item ->
            getAllOrders(list.minus(item)).map {
                list -> list.plus(item)
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val changes = loadChanges()

        val pairingCombinations = getAllPairings(changes)
        pairingCombinations.forEach { println(it) }

        val maxHappiness = pairingCombinations.map { it.sumOf { pairing -> pairing.getHappinessChange(changes) } }
                .maxOf { it }

        println(maxHappiness)


        println(pairingCombinations.map { it.map { pairing -> pairing.getHappinessChange(changes) } }
                .maxByOrNull { it.sum() })

        val maxHappinessWithMe = pairingCombinations.map { pairings ->
            pairings.map { pairing ->
                pairing.getHappinessChange(changes)
            }.sorted().drop(1).sum()
        }.maxOf { it }


        println(maxHappinessWithMe)
    }

    private fun getAllPairings(changes: Map<String, Map<String, Int>>): Set<Set<Pairing>> {
        val pairingCombinations = getAllOrders(changes.keys.toList()).map { table ->
            table.mapIndexed { position, name ->
                val subsequentName = if (position == table.size - 1) table.first() else table[position + 1]
                Pairing(name, subsequentName)
            }.toSet()
        }.toSet()
        return pairingCombinations
    }

    data class Pairing(val person1: String, val person2: String) {

        fun getHappinessChange(changes: Map<String, Map<String, Int>>) : Int {
            return changes.getValue(person1).getValue(person2) +
                    changes.getValue(person2).getValue(person1)
        }
    }
}