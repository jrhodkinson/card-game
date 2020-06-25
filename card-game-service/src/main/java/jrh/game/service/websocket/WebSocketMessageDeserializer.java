package jrh.game.service.websocket;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class WebSocketMessageDeserializer extends StdDeserializer<WebSocketMessage<?>> {

    protected WebSocketMessageDeserializer() {
        super(WebSocketMessage.class);
    }

    @Override
    public WebSocketMessage<?> deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        JsonNode tree = jsonParser.readValueAsTree();
        String type = tree.get("type").asText();
        WebSocketMessageType<?> messageType = WebSocketMessageType.fromString(type);
        if (NoPayload.class.isAssignableFrom(messageType.getPayloadType())) {
            return new WebSocketMessage(messageType, NoPayload.INSTANCE);
        }
        Object payload = tree.get("payload").traverse(jsonParser.getCodec()).readValueAs(messageType.getPayloadType());
        return new WebSocketMessage(messageType, payload);
    }

}
