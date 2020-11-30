package jrh.game.service.lobby.response;

public class ActiveGamesResponse {

    private final int activeGames;

    public ActiveGamesResponse(int activeGames) {
        this.activeGames = activeGames;
    }

    public int getActiveGames() {
        return activeGames;
    }
}
