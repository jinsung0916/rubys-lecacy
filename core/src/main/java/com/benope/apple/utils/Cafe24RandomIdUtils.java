package com.benope.apple.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class Cafe24RandomIdUtils {

    private final static int ID_MIN_LENGTH = 4;
    private final static int ID_MAX_LENGTH = 16;

    public static String getRandomId() {
        return RandomStringUtils.randomAlphanumeric(ID_MIN_LENGTH, ID_MAX_LENGTH).toLowerCase();
    }

}
