package jrh.game.match;

import jrh.game.action.Action;
import jrh.game.card.Store;

public class Match {

    private Store store = new Store();
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
        firstPlayerIsActive = !firstPlayerIsActive;
        currentTurn = new Turn();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(firstPlayer);
        if (firstPlayerIsActive) {
            builder.append(" (*)");
        }
        builder.append("\n");
        builder.append(secondPlayer);
        if (!firstPlayerIsActive) {
            builder.append(" (*)");
        }
        builder.append("\n");
        builder.append(store.toString()).append("\n");
        builder.append(currentTurn.getMoney()).append(" money");
        return builder.toString();
    }
}
