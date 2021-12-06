package com.github.zanvork.aoc2015.solutions.day09

import com.github.zanvork.aoc2015.utils.InputUtil


class PathFinder(private val distanceMap: Map<String, Map<String, Int>>) {

    private val locations = distanceMap.keys

    fun findAllRoutes(): Set<List<String>> {
        return getAllCombinations(locations.toList())
    }

    fun findShortestRoute(): Int {
        return getRouteLengths().minOf { (_, distance) -> distance }
    }

    fun findLongestRoute(): Int {
        return getRouteLengths().maxOf { (_, distance) -> distance }
    }

    private fun getRouteLengths(): List<Pair<List<String>, Int>> {
        return findAllRoutes().map { route ->
            var totalDistance = 0
            for (i in 0 .. route.size - 2) {
                totalDistance += distanceMap.getValue(route[i]).getValue(route[i+1])
            }
            route to totalDistance
        }
    }

    private fun getAllCombinations(list: List<String>): Set<List<String>> {
        if (list.size == 1) {
            return setOf(list)
        }
        return list.flatMap { a ->
            val sublist = list.minus(a)
            getAllCombinations(sublist).map { combos ->
                combos.plus(a)
            }
        }.toSet()
    }

    companion object {

        private val ROUTE_REGEX = "^([A-Za-z]+) to ([A-Za-z]+) = (\\d+)$".toRegex()

        @JvmStatic
        fun main(args: Array<String>) {
            val distanceMap = mutableMapOf<String, MutableMap<String, Int>>()
            InputUtil.readLines("2015/day09input.txt").forEach {
                ROUTE_REGEX.find(it)?.let { matchResult ->
                    val locationA = matchResult.groupValues[1]
                    val locationB = matchResult.groupValues[2]
                    val distance = matchResult.groupValues[3].toInt()
                    distanceMap.getOrPut(locationA) {
                        mutableMapOf()
                    }[locationB] = distance
                    distanceMap.getOrPut(locationB) {
                        mutableMapOf()
                    }[locationA] = distance
                }
            }

            val pathFinder = PathFinder(distanceMap)

            println(pathFinder.findShortestRoute())
            println(pathFinder.findLongestRoute())



        }
    }


}