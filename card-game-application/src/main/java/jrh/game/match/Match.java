package jrh.game.match;

import jrh.game.action.Action;
import jrh.game.deck.DiscardPile;
import jrh.game.deck.Store;
import jrh.game.util.Constants;

import static org.apache.commons.lang3.RandomUtils.nextBoolean;

public class Match {

    private final Store store;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private boolean firstPlayerIsActive;
    private Turn currentTurn;
    private boolean isOver = false;
    private Player winner = null;

    public Match(Store store, Player firstPlayer, Player secondPlayer) {
        this.store = store;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.currentTurn = new Turn();
    }

    public void accept(Action action) {
        if (!isOver) {
            action.applyTo(this);
            if (matchShouldEnd()) {
                end();
            }
        }
    }

    public void start() {
        firstPlayerIsActive = nextBoolean();
        getActivePlayer().drawToHand(Constants.INITIAL_HAND_SIZE);
        getInactivePlayer().drawToHand(Constants.INITIAL_HAND_SIZE);
    }

    public void advanceToNextTurn() {
        DiscardPile discardPile = getActivePlayer().getDeckAndDiscardPile().getDiscardPile();
        discardPile.addAll(getCurrentTurn().getPlayedCards());
        discardPile.addAll(getActivePlayer().getHand());
        getActivePlayer().getHand().clear();
        getActivePlayer().drawToHand(Constants.INITIAL_HAND_SIZE);
        firstPlayerIsActive = !firstPlayerIsActive;
        currentTurn = new Turn();
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

    public boolean isOver() {
        return isOver;
    }

    public Player getWinner() {
        return winner;
    }

    private boolean matchShouldEnd() {
        return getActivePlayer().getHealth() <= 0 || getInactivePlayer().getHealth() <= 0;
    }

    private void end() {
        isOver = true;
        if (getInactivePlayer().getHealth() <= 0) {
            winner = getActivePlayer();
        } else {
            winner = getInactivePlayer();
        }
    }

    @Override
    public String toString() {
        return getInactivePlayer().getUser() + " (" + getInactivePlayer().getHealth() + ")\n" + currentTurn.getMoney()
                + " money, " + currentTurn.getPlayedCards() + " played\n"
                + getActivePlayer().getDeckAndDiscardPile().getDeck().size() + " in deck, "
                + getActivePlayer().getDeckAndDiscardPile().getDiscardPile() + " in discard";
    }
}
