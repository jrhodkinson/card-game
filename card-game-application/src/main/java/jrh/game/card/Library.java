package jrh.game.card;

import jrh.game.card.behaviour.DamageBehaviour;
import jrh.game.card.behaviour.DrawBehaviour;
import jrh.game.card.behaviour.MoneyBehaviour;
import jrh.game.util.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// TODO load these from somewhere
public class Library {

    public static final List<Card> CARDS;
    static {
        List<Card> cards = new ArrayList<>();
        cards.addAll(moneyCards());
        cards.addAll(damageCards());
        cards.addAll(drawCards());
        CARDS = Collections.unmodifiableList(cards);
    }

    public static Card getCard(String name) {
        Optional<Card> optionalCard = CARDS.stream().filter(card -> card.getName().equals(name)).findFirst();
        if (optionalCard.isPresent()) {
            return optionalCard.get();
        }
        throw new RuntimeException("No card with name: " + name);
    }

    private static List<Card> moneyCards() {
        return List.of(
                Card.card("Copper").withCost(1).withColor(Color.YELLOW).withBehaviour(new MoneyBehaviour(1)).build(),
                Card.card("Silver").withCost(3).withColor(Color.YELLOW).withBehaviour(new MoneyBehaviour(2)).build(),
                Card.card("Gold").withCost(6).withColor(Color.YELLOW).withBehaviour(new MoneyBehaviour(3)).build()
        );
    }

    private static List<Card> damageCards() {
        return List.of(
                Card.card("Knife").withCost(1).withColor(Color.RED).withBehaviour(new DamageBehaviour(1)).build(),
                Card.card("Sword").withCost(3).withColor(Color.RED).withBehaviour(new DamageBehaviour(3)).build()
        );
    }

    private static List<Card> drawCards() {
        return List.of(
                Card.card("Draw 1").withCost(3).withColor(Color.BLUE).withBehaviour(new DrawBehaviour(1)).build(),
                Card.card("Draw 2").withCost(5).withColor(Color.BLUE).withBehaviour(new DrawBehaviour(2)).build(),
                Card.card("Double dip")
                        .withCost(8)
                        .withColor(Color.GREEN)
                        .withBehaviour(new MoneyBehaviour(2))
                        .withBehaviour(new DrawBehaviour(1))
                        .build()
        );
    }
}
