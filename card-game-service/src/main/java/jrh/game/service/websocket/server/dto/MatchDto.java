package jrh.game.service.websocket.server.dto;

import jrh.game.api.Match;
import jrh.game.common.User;

import java.util.Map;

public class MatchDto {

    public final User activeUser;
    public final Map<User, PlayerDto> players;
    public final TurnDto currentTurn;
    public final StoreDto storefront;

    private MatchDto(User activeUser, Map<User, PlayerDto> players, TurnDto currentTurn, StoreDto store) {
        this.activeUser = activeUser;
        this.players = players;
        this.currentTurn = currentTurn;
        this.storefront = store;
    }

    public static MatchDto fromMatch(Match match) {
        User activeUser = match.getActivePlayer().getUser();
        PlayerDto activePlayer = PlayerDto.fromPlayer(match.getActivePlayer());
        PlayerDto inactivePlayer = PlayerDto.fromPlayer(match.getInactivePlayer());
        Map<User, PlayerDto> players = Map.of(activePlayer.user, activePlayer, inactivePlayer.user, inactivePlayer);
        return new MatchDto(activeUser, players, TurnDto.fromTurn(match.getCurrentTurn()),
                StoreDto.fromStore(match.getStore()));
    }
}
