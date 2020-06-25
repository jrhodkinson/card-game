package jrh.game.service.websocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toMap;

public class WebSocketMessageTypes {

    private static final Logger logger = LogManager.getLogger(WebSocketMessageTypes.class);
    private static final Map<String, WebSocketMessageType<?>> types = findWebSocketMessageTypes();

    public static WebSocketMessageType<?> get(String messageType) {
        return types.get(messageType);
    }

    private static Map<String, WebSocketMessageType<?>> findWebSocketMessageTypes() {
        Reflections reflections = new Reflections("jrh.game");
        return reflections.getSubTypesOf(WebSocketMessageTypes.class).stream()
                .flatMap(subtype -> Arrays.stream(subtype.getFields()))
                .filter(field -> Modifier.isPublic(field.getModifiers()))
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .filter(field -> WebSocketMessageType.class.isAssignableFrom(field.getType())).map(field -> {
                    try {
                        return field.get(null);
                    } catch (IllegalAccessException e) {
                        logger.error("IllegalAccessException should not be possible", e);
                        return null;
                    }
                }).filter(Objects::nonNull).map(messageType -> (WebSocketMessageType<?>) messageType)
                .collect(toMap(WebSocketMessageType::toString, t -> t));
    }
}
