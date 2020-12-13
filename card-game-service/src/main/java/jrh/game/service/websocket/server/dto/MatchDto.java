package jrh.game.service.websocket.server.dto;

import jrh.game.api.Match;
import jrh.game.common.User;

import java.util.Map;

public class MatchDto {

    public final User activeUser;
    public final Map<User, PlayerDto> players;
    public final TurnDto currentTurn;
    public final StoreDto storefront;
    public final String goAway = "Yes, I know you can see the other player's cards. I'll fix this before the game goes live.";

    private MatchDto(User activeUser, Map<User, PlayerDto> players, TurnDto currentTurn, StoreDto store) {
        this.activeUser = activeUser;
        this.players = players;
        this.currentTurn = currentTurn;
        this.storefront = store;
    }

    public static class Factory {

        private final PlayerDto.Factory playerFactory;
        private final TurnDto.Factory turnFactory;
        private final StoreDto.Factory storeFactory;

        public Factory(PlayerDto.Factory playerFactory, TurnDto.Factory turnFactory, StoreDto.Factory storeFactory) {
            this.playerFactory = playerFactory;
            this.turnFactory = turnFactory;
            this.storeFactory = storeFactory;
        }

        public MatchDto matchDto(Match match) {
            User activeUser = match.getActivePlayer().getUser();
            PlayerDto activePlayer = playerFactory.playerDto(match.getActivePlayer());
            PlayerDto inactivePlayer = playerFactory.playerDto(match.getInactivePlayer());
            Map<User, PlayerDto> players = Map.of(activePlayer.user, activePlayer, inactivePlayer.user, inactivePlayer);
            return new MatchDto(activeUser, players, turnFactory.turnDto(match.getCurrentTurn()),
                    storeFactory.storeDto(match.getStore()));
        }
    }
}
