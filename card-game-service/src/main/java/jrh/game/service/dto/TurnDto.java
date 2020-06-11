package jrh.game.service.dto;

import jrh.game.api.Turn;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class TurnDto {

    private final int money;
    private final List<CardDto> playedCards;

    private TurnDto(int money, List<CardDto> playedCards) {
        this.money = money;
        this.playedCards = playedCards;
    }

    public static TurnDto fromTurn(Turn turn) {
        return new TurnDto(turn.getMoney(), turn.getPlayedCards().stream().map(CardDto::fromCard).collect(toList()));
    }

    public int getMoney() {
        return money;
    }

    public List<CardDto> getPlayedCards() {
        return playedCards;
    }
}
