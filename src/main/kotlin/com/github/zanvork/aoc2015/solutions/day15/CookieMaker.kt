package com.github.zanvork.aoc2015.solutions.day15

import com.github.zanvork.aoc2015.utils.InputUtil

object CookieMaker {
    private val INGREDIENT_REGEX = "^([A-Za-z]+): capacity (-?\\d+), durability (-?\\d+), flavor (-?\\d+), texture (-?\\d+), calories (-?\\d+)$".toRegex()


    fun getIngredients(): List<Ingredient> {
        val rawLines = InputUtil.readLines("2015/day15input.txt")

        return rawLines.map { line ->
            INGREDIENT_REGEX.find(line)?.groupValues?.let { values ->
                Ingredient(
                        values[1],
                        values[2].toInt(),
                        values[3].toInt(),
                        values[4].toInt(),
                        values[5].toInt(),
                        values[6].toInt()
                )
            } ?: throw IllegalArgumentException("Does not match regex")
        }
    }

    fun getCombinations(ingredients: List<Ingredient>, size: Int): List<Map<Ingredient, Int>> {
        val ingredient = ingredients.first()
        if (ingredients.size == 1) {
            return listOf(mapOf(ingredient to size))
        }
        val remaining = ingredients.drop(1)
        return (1..size - remaining.size).flatMap { i ->
            getCombinations(remaining, size - i).map {
                it.plus(ingredient to i)
            }
        }

    }

    @JvmStatic
    fun main(args: Array<String>) {
        val ingredients = getIngredients()

        val combinations = getCombinations(ingredients, 100)
        val allScores = combinations.map { combo -> getScores(combo) }


        println(allScores.maxOf { score -> score.capacity * score.flavor * score.durability * score.texture })
    }

    fun getScores(combination: Map<Ingredient, Int>): Scores {
        var capacity = 0
        var durability = 0
        var flavor = 0
        var texture = 0

        combination.forEach { (ingredient, count) ->
            capacity += ingredient.capacity * count
            durability += ingredient.durability * count
            flavor += ingredient.flavor * count
            texture += ingredient.texture * count
        }
        return Scores(
                maxOf(0, capacity),
                maxOf(0, durability),
                maxOf(0, flavor),
                maxOf(0, texture),
        )

    }


    data class Scores(val capacity: Int,
                      val durability: Int,
                      val flavor: Int,
                      val texture: Int)

    data class Ingredient(val name: String,
                          val capacity: Int,
                          val durability: Int,
                          val flavor: Int,
                          val texture: Int,
                          val calories: Int)
}