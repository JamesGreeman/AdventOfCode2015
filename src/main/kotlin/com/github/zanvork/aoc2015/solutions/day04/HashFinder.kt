package com.github.zanvork.aoc2015.solutions.day04

import java.security.MessageDigest

object HashFinder {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = "bgvyzdsv"

        println(findLowestHashWithPrefix(input, "00000"))
        println(findLowestHashWithPrefix(input, "000000"))
    }

    fun findLowestHashWithPrefix(input: String, prefix: String): Int {
        var i = 0
        var found = false

        while(!found) {
            i++
            val toHash = "$input$i"
            val md5 = MessageDigest.getInstance("MD5")
                    .digest(toHash.toByteArray()).toHex()
            println("$toHash: $md5")
            if (md5.startsWith(prefix)) {
                found = true
            }
        }
        return i
    }

    private fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }
}