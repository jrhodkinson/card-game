package jrh.game.match;

import jrh.game.deck.Store;
import jrh.game.event.EventHandler;
import jrh.game.event.bus.EventBus;

import static org.apache.commons.lang3.RandomUtils.nextBoolean;

public class Match implements EventHandler {

    private final CardFlowManager cardFlowManager;
    private final MatchFlowManager matchFlowManager;
    private final Store store;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private boolean firstPlayerIsActive;
    private Turn currentTurn;
    private boolean isOver = false;
    private Player winner = null;

    public Match(EventBus eventBus, Store store, Player firstPlayer, Player secondPlayer) {
        this.cardFlowManager = new CardFlowManager(eventBus, this);
        this.matchFlowManager = new MatchFlowManager(eventBus, this);
        this.store = store;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.currentTurn = new Turn();
    }

    public CardFlowManager getCardFlowManager() {
        return cardFlowManager;
    }

    public MatchFlowManager getMatchFlowManager() {
        return matchFlowManager;
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

    public void accept() {
        if (!isOver) {
            if (matchShouldEnd()) {
                end();
            }
        }
    }

    void start() {
        firstPlayerIsActive = nextBoolean();
    }

    void advanceToNextTurn() {
        firstPlayerIsActive = !firstPlayerIsActive;
        currentTurn = new Turn();
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
