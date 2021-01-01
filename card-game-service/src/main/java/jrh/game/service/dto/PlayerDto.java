package jrh.game.service.dto;

import jrh.game.api.Card;
import jrh.game.api.Player;
import jrh.game.common.EntityId;
import jrh.game.common.User;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class PlayerDto {

    public final EntityId entityId;
    public final User user;
    public final int health;
    public final List<StructureDto> structures;
    public final List<CardDto> hand;
    public final List<CardDto> discardPile;
    public final List<CardDto> deck;

    private PlayerDto(EntityId entityId, User user, int health, List<StructureDto> structures, List<CardDto> hand,
            List<CardDto> discardPile, List<CardDto> deck) {
        this.entityId = entityId;
        this.user = user;
        this.health = health;
        this.structures = structures;
        this.hand = hand;
        this.discardPile = discardPile;
        this.deck = deck;
    }

    public static class Factory {

        private final CardDto.Factory cardFactory;
        private final StructureDto.Factory structureFactory;

        public Factory(CardDto.Factory cardFactory, StructureDto.Factory structureFactory) {
            this.cardFactory = cardFactory;
            this.structureFactory = structureFactory;
        }

        public PlayerDto playerDto(Player player) {
            List<CardDto> alphabeticalDeck = player.getDeck().stream().sorted(Comparator.comparing(Card::getName))
                    .map(cardFactory::cardDto).collect(toList());
            return new PlayerDto(player.getEntityId(), player.getUser(), player.getHealth(),
                    player.getStructures().stream().map(structureFactory::structureDto).collect(toList()),
                    cardFactory.cardDtos(player.getHand()), cardFactory.cardDtos(player.getDiscardPile()),
                    alphabeticalDeck);
        }
    }

}
