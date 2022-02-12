package com.devendrabrain.StudentMgr.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;


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
