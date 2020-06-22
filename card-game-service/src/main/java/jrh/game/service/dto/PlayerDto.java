package jrh.game.service.dto;

import jrh.game.api.Player;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class PlayerDto {

    private final String username;
    private final int health;
    private final List<StructureDto> structures;
    private final List<CardDto> hand;
    private final List<CardDto> discardPile;
    private final int deckSize;

    private PlayerDto(String username, int health, List<StructureDto> structures, List<CardDto> hand,
            List<CardDto> discardPile, int deckSize) {
        this.username = username;
        this.health = health;
        this.structures = structures;
        this.hand = hand;
        this.discardPile = discardPile;
        this.deckSize = deckSize;
    }

    public static PlayerDto fromPlayer(Player player) {
        return new PlayerDto(player.getUser().getName(), player.getHealth(),
                player.getStructures().stream().map(StructureDto::fromStructure).collect(toList()),
                CardDto.fromCards(player.getHand()), CardDto.fromCards(player.getDiscardPile()),
                player.getDeck().size());
    }

    public String getUsername() {
        return username;
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
