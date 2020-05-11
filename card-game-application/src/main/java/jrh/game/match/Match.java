package jrh.game.match;

import jrh.game.action.Action;
import jrh.game.deck.DiscardPile;
import jrh.game.deck.Store;
import jrh.game.util.Constants;

public class Match {

    private final Store store = new Store(Constants.STORE_SIZE);
    private final Player firstPlayer;
    private final Player secondPlayer;
    private boolean firstPlayerIsActive = true;
    private Turn currentTurn;

    public Match(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.currentTurn = new Turn();
    }

    public void accept(Action action) {
        action.applyTo(this);
    }

    public Store getStore() {
        return store;
    }

    public Turn getCurrentTurn() {
        return currentTurn;
    }

    public Player getActivePlayer() {
        return firstPlayerIsActive ? firstPlayer : secondPlayer;
    }

    public Player getInactivePlayer() {
        return firstPlayerIsActive ? secondPlayer : firstPlayer;
    }

    public void advanceToNextTurn() {
        DiscardPile discardPile = getActivePlayer().getDeckAndDiscardPile().getDiscardPile();
        discardPile.addAll(getCurrentTurn().getPlayedCards());
        discardPile.addAll(getActivePlayer().getHand());
        getActivePlayer().getHand().clear();
        firstPlayerIsActive = !firstPlayerIsActive;
        currentTurn = new Turn();
        while (getActivePlayer().getHand().size() < Constants.INITIAL_HAND_SIZE && getActivePlayer().drawToHand()) {
            // intentionally empty
        }
    }

    @Override
    public String toString() {
        return getInactivePlayer().getName() + " (" + getInactivePlayer().getHealth() + ")\n" +
                currentTurn.getPlayedCards() + " played\n" +
                getActivePlayer().getDeckAndDiscardPile().getDeck().size() + " in deck\n" +
                getActivePlayer().getDeckAndDiscardPile().getDiscardPile() + " in discard\n" +
                currentTurn.getMoney() + " money";
    }
}
