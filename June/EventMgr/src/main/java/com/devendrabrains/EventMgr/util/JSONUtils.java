package com.devendrabrains.EventMgr.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JSONUtils {

    private static Logger logger = LoggerFactory.getLogger(JSONUtils.class);


    @JsonCreator
    public static <T> T toObject(String json, Class<T> returnClass) throws IOException {
        ObjectMapper obj = new ObjectMapper();
        return obj.readValue(json, returnClass);

    }

    public static String toJson(Object object) throws JsonProcessingException {
        ObjectMapper obj = new ObjectMapper();
        return obj.writeValueAsString(object);
    }
}
