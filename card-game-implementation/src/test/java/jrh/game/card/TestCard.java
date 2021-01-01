package jrh.game.card;

import jrh.game.card.behaviour.AbstractBehaviour;
import jrh.game.common.CardId;

import java.util.UUID;

public class TestCard {

    public static CardImpl forBehaviour(AbstractBehaviour behaviour) {
        return CardImpl
            .card(new CardId(UUID.randomUUID().toString()))
            .withName("test card")
            .withCost(0)
            .isPurchasable(true)
            .withBehaviour(behaviour)
            .build();
    }
}
