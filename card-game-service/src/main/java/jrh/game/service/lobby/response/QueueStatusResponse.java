package jrh.game.service.lobby.response;

import java.util.UUID;

public class QueueStatusResponse {

    private final String type;
    private final UUID matchId;

    private QueueStatusResponse(String type, UUID matchId) {
        this.type = type;
        this.matchId = matchId;
    }

    public static QueueStatusResponse inMatch(UUID matchId) {
        return new QueueStatusResponse("in match", matchId);
    }

    public static QueueStatusResponse queueing() {
        return new QueueStatusResponse("in queue", null);
    }

    public static QueueStatusResponse notInQueue() {
        return new QueueStatusResponse("not in queue", null);
    }

    public static QueueStatusResponse downForMaintenance() {
        return new QueueStatusResponse("down for maintenance", null);
    }

    public String getType() {
        return type;
    }

    public UUID getMatchId() {
        return matchId;
    }
}
