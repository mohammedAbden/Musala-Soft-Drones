package com.musala.soft.drones.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegexUtilsTest {

    @Test
    void validateRegex_LETTERS_NUMBERS_UNDERSCORE_MINUS() {

        assertTrue(RegexUtils.validateRegex("", RegexUtils.LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertTrue(RegexUtils.validateRegex("abcdefghijklmnopqrstuvwxyz", RegexUtils.LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertTrue(RegexUtils.validateRegex("ABCDEFGHIJKLMNOPQRSTUVWXYZ", RegexUtils.LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertTrue(RegexUtils.validateRegex("1234567890", RegexUtils.LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertTrue(RegexUtils.validateRegex("_", RegexUtils.LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertTrue(RegexUtils.validateRegex("-", RegexUtils.LETTERS_NUMBERS_UNDERSCORE_MINUS));

        assertFalse(RegexUtils.validateRegex(".", RegexUtils.LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("!", RegexUtils.LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("@", RegexUtils.LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("#", RegexUtils.LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("$", RegexUtils.LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("%", RegexUtils.LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("^", RegexUtils.LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("&", RegexUtils.LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("*", RegexUtils.LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("(", RegexUtils.LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex(")", RegexUtils.LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("+", RegexUtils.LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("*", RegexUtils.LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("/", RegexUtils.LETTERS_NUMBERS_UNDERSCORE_MINUS));
    }

    @Test
    void validateRegex_UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS() {

        assertTrue(RegexUtils.validateRegex("", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertTrue(RegexUtils.validateRegex("ABCDEFGHIJKLMNOPQRSTUVWXYZ", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertTrue(RegexUtils.validateRegex("1234567890", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertTrue(RegexUtils.validateRegex("_", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));

        assertFalse(RegexUtils.validateRegex("-", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex(".", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("!", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("@", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("#", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("$", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("%", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("^", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("&", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("*", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("(", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex(")", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("+", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("*", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("/", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));

        assertFalse(RegexUtils.validateRegex("a", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("b", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("c", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("d", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("f", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("g", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("h", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("i", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("j", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("k", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("l", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("m", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("n", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("o", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("p", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("q", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("r", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("s", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("t", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("u", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("v", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("w", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("x", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("y", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));
        assertFalse(RegexUtils.validateRegex("z", RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE_MINUS));




    }

}