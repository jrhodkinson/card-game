package jrh.game.match;

import jrh.game.User;
import jrh.game.card.CardBehaviourRegistrar;
import jrh.game.card.CardFactory;
import jrh.game.card.Library;
import jrh.game.deck.Store;
import jrh.game.event.bus.EventBus;
import jrh.game.util.Constants;

import java.util.Collections;
import java.util.Random;

import static org.apache.commons.lang3.RandomUtils.nextBoolean;

public class Match {

    private final EventBus eventBus;
    private final CardFlowController cardFlowController;
    private final MatchStateController matchStateController;
    private final HealthController healthController;
    private final CardFactory cardFactory;
    private final Store store;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private boolean firstPlayerIsActive;
    private Turn currentTurn;
    private boolean isOver = false;
    private Player winner = null;

    public Match(Library library, User firstUser, User secondUser) {
        this.eventBus = new EventBus(this);
        this.cardFlowController = new CardFlowController(this);
        this.matchStateController = new MatchStateController(this);
        matchStateController.registerWith(eventBus);
        this.healthController = new HealthController(this);
        eventBus.register(new CardBehaviourRegistrar());
        this.cardFactory = new CardFactory(eventBus, library, new Random());
        this.store = new Store(cardFactory, Constants.STORE_SIZE, Collections.emptyList());
        this.firstPlayer = new Player(firstUser, cardFactory.startingDeck());
        this.secondPlayer = new Player(secondUser, cardFactory.startingDeck());
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

    public HealthController getHealthController() {
        return healthController;
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

    public Player getOtherPlayer(Player player) {
        if (player.equals(firstPlayer)) {
            return secondPlayer;
        }
        return firstPlayer;
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
