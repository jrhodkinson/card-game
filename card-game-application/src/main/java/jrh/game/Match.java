package jrh.game;

import jrh.game.action.Action;

public class Match {

    private final Player firstPlayer;
    private final Player secondPlayer;
    private boolean firstPlayerIsActive = true;
    private Turn currentTurn;

    public Match(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.currentTurn = new Turn(firstPlayer, secondPlayer);
    }

    public void accept(Action action) {
        action.applyTo(this);
    }

    public Turn getCurrentTurn() {
        return currentTurn;
    }

    public void advanceToNextTurn() {
        firstPlayerIsActive = !firstPlayerIsActive;
        if (firstPlayerIsActive) {
            currentTurn = new Turn(firstPlayer, secondPlayer);
        } else {
            currentTurn = new Turn(secondPlayer, firstPlayer);
        }
    }
}
