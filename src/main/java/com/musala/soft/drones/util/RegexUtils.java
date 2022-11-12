package com.musala.soft.drones.util;

import java.util.regex.Pattern;

public final class RegexUtils {

    public final static String LETTERS_NUMBERS_UNDERSCORE_MINUS = "^[a-zA-Z0-9_-]*$";

    public static final String UPPER_LETTERS_NUMBERS_UNDERSCORE = "^[A-Z0-9_]*$";

    private RegexUtils() {

    }

    public static Boolean validateRegex(String data, String regexPattern) {

        return Pattern.compile(regexPattern)
                .matcher(data).matches();

    }

}
