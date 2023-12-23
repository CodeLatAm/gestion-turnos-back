package com.getion.turnos.util;

public class Operations {

    public static String trimBrackets(String message) {

        return message.replaceAll("[\\[\\]]", "");
    }
}
