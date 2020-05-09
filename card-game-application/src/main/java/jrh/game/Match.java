package jrh.game;

import jrh.game.action.Action;

public class Match {

    private final Player firstPlayer;
    private final Player secondPlayer;
    private boolean firstPlayerIsActive = true;

    public Match(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public void accept(Action action) {
        action.applyTo(this);
    }

    public Player activePlayer() {
        return firstPlayerIsActive ? firstPlayer : secondPlayer;
    }

    public Player inactivePlayer() {
        return firstPlayerIsActive ? secondPlayer : firstPlayer;
    }

    public void advanceToNextPlayer() {
        firstPlayerIsActive = !firstPlayerIsActive;
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
        return builder.toString();
    }
}
