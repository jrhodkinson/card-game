package jrh.game.deck;

import jrh.game.card.Card;

public class Pile {

    private final Card card;
    private int quantity;

    public Pile(Card card, int quantity) {
        this.card = card;
        this.quantity = quantity;
    }

    public boolean take() {
        if (quantity > 0) {
            quantity--;
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return quantity == 0;
    }

    public Card getCard() {
        return card;
    }

    public int getQuantity() {
        return quantity;
    }

}
