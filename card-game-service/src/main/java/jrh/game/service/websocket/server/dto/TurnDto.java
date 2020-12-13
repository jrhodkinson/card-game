package jrh.game.service.websocket.server.dto;

import jrh.game.api.Turn;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class TurnDto {

    public final int money;
    public final List<CardDto> playedCards;

    private TurnDto(int money, List<CardDto> playedCards) {
        this.money = money;
        this.playedCards = playedCards;
    }

    public static class Factory {

        private final CardDto.Factory cardFactory;

        public Factory(CardDto.Factory cardFactory) {
            this.cardFactory = cardFactory;
        }

        public TurnDto turnDto(Turn turn) {
            return new TurnDto(turn.getMoney(),
                    turn.getPlayedCards().stream().map(cardFactory::cardDto).collect(toList()));
        }
    }
}
