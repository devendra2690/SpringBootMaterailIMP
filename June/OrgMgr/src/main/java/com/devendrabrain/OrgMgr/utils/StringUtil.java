package com.devendrabrain.OrgMgr.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {

    private static final String WILDCARD = "%";

    public static String getMatchString(String inputString, String patternString) {

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(inputString);
        // if pattern found return the string else empty string
        String res = (matcher.find()) ? matcher.group() : "";
        return res;
    }
}
