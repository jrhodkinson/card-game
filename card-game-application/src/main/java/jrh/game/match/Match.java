package jrh.game.match;

import jrh.game.deck.Store;
import jrh.game.event.bus.EventBus;

import static org.apache.commons.lang3.RandomUtils.nextBoolean;

public class Match {

    private final EventBus eventBus;
    private final CardFlowController cardFlowController;
    private final MatchStateController matchStateController;
    private final DamageController damageController;
    private final Store store;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private boolean firstPlayerIsActive;
    private Turn currentTurn;
    private boolean isOver = false;
    private Player winner = null;

    public Match(Store store, Player firstPlayer, Player secondPlayer) {
        this.eventBus = new EventBus(this);
        this.cardFlowController = new CardFlowController(this);
        this.matchStateController = new MatchStateController(this);
        matchStateController.registerWith(eventBus);
        this.damageController = new DamageController(this);
        this.store = store;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.currentTurn = new Turn();
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public CardFlowController getCardFlowController() {
        return cardFlowController;
    }

    public MatchStateController getMatchStateController() {
        return matchStateController;
    }

    public DamageController getDamageController() {
        return damageController;
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

    void start() {
        firstPlayerIsActive = nextBoolean();
    }

    void advanceToNextTurn() {
        firstPlayerIsActive = !firstPlayerIsActive;
        currentTurn = new Turn();
    }

    void end() {
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
