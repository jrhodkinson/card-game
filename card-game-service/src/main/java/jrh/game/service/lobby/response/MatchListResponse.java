package jrh.game.service.lobby.response;

import jrh.game.service.lobby.MatchMetadata;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MatchListResponse {

    private final List<MatchMetadataDto> matches;

    public MatchListResponse(List<MatchMetadata> matches) {
        this.matches = matches.stream().map(MatchMetadataDto::from).collect(Collectors.toList());
    }

    public List<MatchMetadataDto> getMatches() {
        return matches;
    }

    public static class MatchMetadataDto {

        private final UUID id;
        private final List<String> players;

        private MatchMetadataDto(UUID id, List<String> players) {
            this.id = id;
            this.players = players;
        }

        public static MatchMetadataDto from(MatchMetadata matchMetadata) {
            return new MatchMetadataDto(matchMetadata.getMatchId(), matchMetadata.getPlayers());
        }

        public UUID getId() {
            return id;
        }

        public List<String> getPlayers() {
            return players;
        }
    }

}
