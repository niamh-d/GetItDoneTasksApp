package dev.niamhdoyle.getitdone.util

import android.text.Editable

object InputValidator {

    fun isInputValid(input: String?): Boolean {
        val trimmedInput = input?.trim()
        return !trimmedInput.isNullOrEmpty() && trimmedInput.length > 1
    }
}