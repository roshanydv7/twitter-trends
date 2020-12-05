package com.twitter.utils;

import com.twitter.constants.TwitterConstants;

public class ObjectUtils {

    public static boolean isNumber(String inputLines) {
        return inputLines.matches(TwitterConstants.NUMBER_REGEX);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
