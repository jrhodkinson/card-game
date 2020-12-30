package jrh.game.service.lobby.response;

public class MatchCountResponse {

    private final int activeMatches;

    public MatchCountResponse(int activeMatches) {
        this.activeMatches = activeMatches;
    }

    public int getActiveMatches() {
        return activeMatches;
    }
}
