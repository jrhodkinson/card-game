package jrh.game.service.websocket.server.dto;

import jrh.game.api.Match;

public class MatchDto {

    public final PlayerDto activePlayer;
    public final PlayerDto inactivePlayer;
    public final TurnDto currentTurn;
    public final StoreDto storefront;

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
}
