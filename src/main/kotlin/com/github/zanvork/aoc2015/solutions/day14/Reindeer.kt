package com.github.zanvork.aoc2015.solutions.day14

import com.github.zanvork.aoc2015.utils.InputUtil
import java.lang.Integer.min

data class Reindeer(private val name: String,
               private val speed: Int,
               private val runningTime: Int,
               private val restingTime: Int) {

    fun getFullCycleTime() = runningTime + restingTime
    fun getDistanceAfterSeconds(seconds: Int): Int {
        val fullCycle = getFullCycleTime()
        val fullCycles = seconds / fullCycle
        val incompleteTime = min(runningTime, seconds % fullCycle)
        return speed * runningTime * fullCycles + speed * incompleteTime
    }


    companion object {
        private val REINDEER_REGEX = "([a-zA-Z]+) can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds.".toRegex()

        fun parse(input: String) : Reindeer {
            return REINDEER_REGEX.find(input)?.let { matchResult ->
                val name = matchResult.groupValues[1]
                val speed = matchResult.groupValues[2].toInt()
                val runningTime = matchResult.groupValues[3].toInt()
                val restingTime = matchResult.groupValues[4].toInt()

                Reindeer(name, speed, runningTime, restingTime)
            } ?: throw IllegalArgumentException("Input does not match regex.")
        }


        @JvmStatic
        fun main(args: Array<String>) {
            val reindeer = InputUtil.readLines("2015/day14input.txt").map { parse(it) }

            println(reindeer.maxOf { it.getDistanceAfterSeconds(2503)})

            val trackedReindeerList = reindeer.map { ReindeerTracker(it) }
            val reindeerScore = reindeer.associateWith { 0 }.toMutableMap()

            for (i in (1..2503)) {
                trackedReindeerList.forEach { trackedReindeer -> trackedReindeer.nextSecond() }
                val winningDistance = trackedReindeerList.maxOf { trackedReindeer -> trackedReindeer.distanceTravelled }
                for (trackedReindeer in trackedReindeerList) {
                    if (trackedReindeer.distanceTravelled == winningDistance) {
                        reindeerScore[trackedReindeer.reindeer] = reindeerScore.getValue(trackedReindeer.reindeer) + 1
                    }
                }
            }
            println(reindeerScore.values.maxOf { it } )
        }
    }

    class ReindeerTracker(val reindeer: Reindeer) {

        var elapsedSeconds = 0
            private set
        var distanceTravelled = 0
            private set

        fun nextSecond() {
            elapsedSeconds++
            val fullCycle = reindeer.getFullCycleTime()
            val modulo = elapsedSeconds % fullCycle

            if (modulo > 0 && modulo <= reindeer.runningTime) {
                distanceTravelled += reindeer.speed
            }

        }

        override fun toString(): String {
            return "ReindeerTracker(reindeer=$reindeer, elapsedSeconds=$elapsedSeconds, distanceTravelled=$distanceTravelled)"
        }


    }
}