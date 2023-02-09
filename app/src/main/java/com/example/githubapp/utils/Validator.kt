package com.example.githubapp.utils

object Validator {
    fun validateNickname(text: String?) : Boolean {
        return if (text == null) false
        else if (text.length < 3 || text.length > 39) false
        else !('!' in text || '.' in text || '?' in text || ',' in text ||
                '(' in text || ')' in text || '/' in text || '|' in text ||
                ';' in text || ':' in text || '_' in text)
    }
}