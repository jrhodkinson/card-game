package jrh.game.service.dto;

import jrh.game.api.Match;

public class MatchDto {

    private final PlayerDto activePlayer;
    private final PlayerDto inactivePlayer;
    private final TurnDto currentTurn;

    private MatchDto(PlayerDto activePlayer, PlayerDto inactivePlayer, TurnDto currentTurn) {
        this.activePlayer = activePlayer;
        this.inactivePlayer = inactivePlayer;
        this.currentTurn = currentTurn;
    }

    public static MatchDto fromMatch(Match match) {
        return new MatchDto(
                PlayerDto.fromPlayer(match.getActivePlayer()),
                PlayerDto.fromPlayer(match.getInactivePlayer()),
                TurnDto.fromTurn(match.getCurrentTurn())
        );
    }

    public PlayerDto getActivePlayer() {
        return activePlayer;
    }

    public PlayerDto getInactivePlayer() {
        return inactivePlayer;
    }

    public TurnDto getCurrentTurn() {
        return currentTurn;
    }
}
