package jrh.game.common;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperFactory {

    public static ObjectMapper create() {
        return new ObjectMapper();
    }
}
