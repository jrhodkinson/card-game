package jrh.game.card;

import jrh.game.card.behaviour.AbstractBehaviour;
import jrh.game.common.CardId;
import jrh.game.common.Color;

import java.util.UUID;

public class TestCard {

    public static CardImpl forBehaviour(AbstractBehaviour behaviour) {
        return CardImpl.card(new CardId(UUID.randomUUID().toString())).withName("test card").withCost(1)
                .withColor(Color.BLACK).withBehaviour(behaviour).build();
    }
}
