package jrh.game.match;

import jrh.game.User;
import jrh.game.card.CardBehaviourRegistrar;
import jrh.game.card.CardFactory;
import jrh.game.card.Library;
import jrh.game.deck.Store;
import jrh.game.event.EventBus;
import jrh.game.structure.StructureHealthController;
import jrh.game.structure.StructureStateController;
import jrh.game.util.Constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.apache.commons.lang3.RandomUtils.nextBoolean;

public class Match {

    private final EventBus eventBus;
    private final Map<Class<? extends Controller>, Controller> controllers = new HashMap<>();
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

    @SuppressWarnings("unchecked")
    public <T extends Controller> T getController(Class<T> controllerClass) {
        return (T) controllers.get(controllerClass);
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

    public void start() {
        setUpControllers();
        firstPlayerIsActive = nextBoolean();
        getController(MatchStateController.class).startMatch();
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

    private void setUpControllers() {
        List.of(new CardFlowController(this), new MatchStateController(this), new PlayerHealthController(this),
                new StructureHealthController(this), new StructureStateController(this)).forEach(this::putController);
        controllers.values().forEach(Controller::initialise);
    }

    private void putController(Controller controller) {
        this.controllers.put(controller.getClass(), controller);
    }

    @Override
    public String toString() {
        return getActivePlayer().getUser() + " (" + getActivePlayer().getHealth() + ") vs " + getInactivePlayer().getUser() + " (" + getInactivePlayer().getHealth() + ")\n" + currentTurn.getMoney()
                + " money, " + currentTurn.getPlayedCards() + " played\n"
                + getActivePlayer().getDeckAndDiscardPile().getDeck().size() + " in deck, "
                + getActivePlayer().getDeckAndDiscardPile().getDiscardPile() + " in discard\n"
                + "active structures: " + getActivePlayer().getStructures().toString() + "\n"
                + "inactive structures: " + getInactivePlayer().getStructures().toString();
    }
}
