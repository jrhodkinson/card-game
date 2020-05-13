package jrh.game.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperFactory {

    public static ObjectMapper create() {
        return new ObjectMapper();
    }
}
