package com.companioncar.backstage.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONUtil {

    protected static Logger logger  = LoggerFactory.getLogger(JSONUtil.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String stringify(Object object){
        String stringify = null;
        try {
            stringify = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        return stringify;
    }

    public static <T>T parse(String jsonStr,Class<T> c){
        T t = null;
        try {
            t = objectMapper.readValue(jsonStr, c);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        return t;
    }
}
