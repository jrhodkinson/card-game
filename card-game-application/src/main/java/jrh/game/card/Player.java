package jrh.game.card;

public class Player {

    private final Hand hand;

    public Player(Hand hand) {
        this.hand = hand;
    }

    public Hand getHand() {
        return hand;
    }
}
