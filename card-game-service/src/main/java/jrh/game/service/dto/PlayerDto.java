package jrh.game.service.dto;

import jrh.game.api.Player;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class PlayerDto {

    private final String username;
    private final int health;
    private final List<StructureDto> structures;
    private final List<CardDto> hand;
    private final int deckSize;
    private final int discardPileSize;

    private PlayerDto(String username, int health, List<StructureDto> structures, List<CardDto> hand, int deckSize,
            int discardPileSize) {
        this.username = username;
        this.health = health;
        this.structures = structures;
        this.hand = hand;
        this.deckSize = deckSize;
        this.discardPileSize = discardPileSize;
    }

    public static PlayerDto fromPlayer(Player player) {
        return new PlayerDto(player.getUser().getName(), player.getHealth(),
                player.getStructures().stream().map(StructureDto::fromStructure).collect(toList()),
                player.getHand().stream().map(CardDto::fromCard).collect(toList()), player.getDeck().size(),
                player.getDiscardPile().size());
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

    public int getDeckSize() {
        return deckSize;
    }

    public int getDiscardPileSize() {
        return discardPileSize;
    }
}
