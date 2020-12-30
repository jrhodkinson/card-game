package jrh.game.service.lobby.response;

import jrh.game.service.lobby.MatchMetadata;

import java.util.List;

public class MatchListResponse {

    private final List<MatchMetadata> matches;

    public MatchListResponse(List<MatchMetadata> matches) {
        this.matches = matches;
    }

    public List<MatchMetadata> getMatches() {
        return matches;
    }

}
