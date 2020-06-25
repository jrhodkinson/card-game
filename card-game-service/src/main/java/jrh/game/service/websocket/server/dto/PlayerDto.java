package jrh.game.service.websocket.server.dto;

import jrh.game.api.Player;
import jrh.game.common.User;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class PlayerDto {

    private final User user;
    private final int health;
    private final List<StructureDto> structures;
    private final List<CardDto> hand;
    private final List<CardDto> discardPile;
    private final int deckSize;

    private PlayerDto(User user, int health, List<StructureDto> structures, List<CardDto> hand,
            List<CardDto> discardPile, int deckSize) {
        this.user = user;
        this.health = health;
        this.structures = structures;
        this.hand = hand;
        this.discardPile = discardPile;
        this.deckSize = deckSize;
    }

    public static PlayerDto fromPlayer(Player player) {
        return new PlayerDto(player.getUser(), player.getHealth(),
                player.getStructures().stream().map(StructureDto::fromStructure).collect(toList()),
                CardDto.fromCards(player.getHand()), CardDto.fromCards(player.getDiscardPile()),
                player.getDeck().size());
    }

    public User getUser() {
        return user;
    }

    public int getHealth() {
        return health;
    }

    public List<StructureDto> getStructures() {
        return structures;
    }

    public List<CardDto> getHand() {
        return hand;
    }

    public List<CardDto> getDiscardPile() {
        return discardPile;
    }

    public int getDeckSize() {
        return deckSize;
    }
}
