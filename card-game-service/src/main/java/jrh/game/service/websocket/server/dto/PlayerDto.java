package jrh.game.service.websocket.server.dto;

import jrh.game.api.Player;
import jrh.game.common.User;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class PlayerDto {

    public final User user;
    public final int health;
    public final List<StructureDto> structures;
    public final List<CardDto> hand;
    public final List<CardDto> discardPile;
    public final int deckSize;

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
}
