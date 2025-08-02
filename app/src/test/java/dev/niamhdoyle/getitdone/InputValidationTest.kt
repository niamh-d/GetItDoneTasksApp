package dev.niamhdoyle.getitdone

import dev.niamhdoyle.getitdone.util.InputValidator
import org.junit.Test
import org.junit.Assert.*

class InputValidationTest {

    @Test
    fun inputValidator_returnsFalseWhenEmpty() {
        assertFalse(InputValidator.isInputValid(""))
    }

    @Test
    fun inputValidator_returnFalseWhenOneCharacter() {
        assertFalse(InputValidator.isInputValid("c"))
    }

    @Test
    fun inputValidator_returnFalseWhenOnlyWhiteSpace() {
        assertFalse(InputValidator.isInputValid("     "))
    }

    @Test
    fun inputValidator_returnFalseWhenOneCharacterAndWhiteSpace() {
        assertFalse(InputValidator.isInputValid(" f    "))
    }

    @Test
    fun inputValidator_returnTrueWhenTwoCharacters() {
        assertTrue(InputValidator.isInputValid("cc"))
    }
}