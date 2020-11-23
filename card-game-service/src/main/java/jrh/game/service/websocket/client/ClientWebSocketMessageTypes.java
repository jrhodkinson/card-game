package jrh.game.service.websocket.client;

import jrh.game.common.EntityId;
import jrh.game.service.websocket.NoPayload;
import jrh.game.service.websocket.WebSocketMessageType;
import jrh.game.service.websocket.WebSocketMessageTypes;
import jrh.game.service.websocket.client.dto.PlayCardDto;

public class ClientWebSocketMessageTypes extends WebSocketMessageTypes {

    public static final WebSocketMessageType<Long> PONG = WebSocketMessageType.of("pong", Long.class);
    public static final WebSocketMessageType<NoPayload> END_TURN = WebSocketMessageType.emptyPayload("turn/end");
    public static final WebSocketMessageType<PlayCardDto> PLAY_CARD = WebSocketMessageType.of("card/play",
            PlayCardDto.class);
    public static final WebSocketMessageType<EntityId> BUY_CARD = WebSocketMessageType.of("card/buy", EntityId.class);
}
