package com.example.githubapp.utils

object Validator {
    fun validateNickname(text: String?): Boolean {
        return if (text == null) false
        else !('!' in text || '.' in text || '?' in text || ',' in text ||
                '(' in text || ')' in text || '/' in text || '|' in text ||
                ';' in text || ':' in text || '_' in text && (text.length !in 3..39))
    }
}