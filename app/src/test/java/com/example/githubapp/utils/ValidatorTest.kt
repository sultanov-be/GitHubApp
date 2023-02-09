package com.example.githubapp.utils

import org.junit.Assert.*
import org.junit.Test

class ValidatorTest {
    @Test
    fun whenInputIsNull() {
        val result = Validator.validateNickname(null)
        assertEquals(false, result)
    }

    @Test
    fun whenInputIsBig() {
        val result = Validator.validateNickname("null1234567891234567890123456789012345678901234567890")
        assertEquals(false, result)
    }

    @Test
    fun whenInputIsSmall() {
        val result = Validator.validateNickname("12")
        assertEquals(false, result)
    }

    @Test
    fun whenInputIsSomeCharacters() {
    val result = Validator.validateNickname("!misconstrue")
        assertEquals(false, result)
    }

    @Test
    fun whenInputIsCorrect() {
        val result = Validator.validateNickname("sultanov-be")
        assertEquals(true, result)
    }
}