package jrh.game.service.dto;

import jrh.game.api.Match;

public class MatchDto {

    private final PlayerDto activePlayer;
    private final PlayerDto inactivePlayer;
    private final TurnDto currentTurn;
    private final StoreDto storefront;

    private MatchDto(PlayerDto activePlayer, PlayerDto inactivePlayer, TurnDto currentTurn, StoreDto store) {
        this.activePlayer = activePlayer;
        this.inactivePlayer = inactivePlayer;
        this.currentTurn = currentTurn;
        this.storefront = store;
    }

    public static MatchDto fromMatch(Match match) {
        return new MatchDto(PlayerDto.fromPlayer(match.getActivePlayer()),
                PlayerDto.fromPlayer(match.getInactivePlayer()), TurnDto.fromTurn(match.getCurrentTurn()),
                StoreDto.fromStore(match.getStore()));
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

    public StoreDto getStorefront() {
        return storefront;
    }
}
