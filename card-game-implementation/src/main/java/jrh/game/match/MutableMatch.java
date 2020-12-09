package jrh.game.match;

import jrh.game.Constants;
import jrh.game.api.ActionHandler;
import jrh.game.api.Controller;
import jrh.game.api.EventBus;
import jrh.game.api.Match;
import jrh.game.api.Player;
import jrh.game.api.Structure;
import jrh.game.asset.AssetLibrary;
import jrh.game.card.CardBehaviourRegistrar;
import jrh.game.card.CardImplFactory;
import jrh.game.common.User;
import jrh.game.deck.MutableStore;
import jrh.game.event.SingleMatchEventBus;
import jrh.game.structure.MutableStructure;
import jrh.game.structure.StructureFactory;
import jrh.game.structure.StructureHealthController;
import jrh.game.structure.StructureStateController;
import jrh.game.structure.Structures;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableList;
import static org.apache.commons.lang3.RandomUtils.nextBoolean;

public class MutableMatch implements Match {

    private final UUID id = UUID.randomUUID();
    private final EventBus eventBus;
    private final ActionHandlerImpl actionHandler;
    private final Map<Class<? extends Controller>, Controller> controllers = new HashMap<>();
    private final ModificationComputer modificationComputer;
    private final CardImplFactory cardImplFactory;
    private final StructureFactory structureFactory;
    private final MutableStore mutableStore;
    private final MutablePlayer firstPlayer;
    private final MutablePlayer secondPlayer;
    private boolean firstPlayerIsActive;
    private MutableTurn currentTurn;
    private boolean isOver = false;
    private MutablePlayer winner = null;

    public MutableMatch(AssetLibrary assetLibrary, User firstUser, User secondUser) {
        this.eventBus = new SingleMatchEventBus(this);
        eventBus.register(new CardBehaviourRegistrar());
        this.actionHandler = new ActionHandlerImpl(this);
        eventBus.register(actionHandler);
        this.modificationComputer = new ModificationComputer(this);
        this.cardImplFactory = new CardImplFactory(eventBus, assetLibrary, new Random());
        this.structureFactory = new StructureFactory(eventBus, assetLibrary);
        this.mutableStore = new MutableStore(cardImplFactory, Constants.STORE_SIZE);
        this.firstPlayer = new MutablePlayer(firstUser, cardImplFactory.startingDeck());
        this.secondPlayer = new MutablePlayer(secondUser, cardImplFactory.startingDeck());
        this.currentTurn = new MutableTurn();
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public ActionHandler getActionHandler() {
        return this.actionHandler;
    }

    @Override
    public <T extends Controller> boolean hasController(Class<T> controllerClass) {
        return controllers.containsKey(controllerClass);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Controller> T getController(Class<T> controllerClass) {
        return (T) controllers.get(controllerClass);
    }

    @Override
    public MutableStore getStore() {
        return mutableStore;
    }

    @Override
    public MutableTurn getCurrentTurn() {
        return currentTurn;
    }

    @Override
    public MutablePlayer getPlayer(User user) {
        if (firstPlayer.getUser().equals(user)) {
            return firstPlayer;
        }
        if (secondPlayer.getUser().equals(user)) {
            return secondPlayer;
        }
        throw new IllegalArgumentException(String.format("User %s is not playing this match", user));
    }

    @Override
    public MutablePlayer getActivePlayer() {
        return firstPlayerIsActive ? firstPlayer : secondPlayer;
    }

    @Override
    public MutablePlayer getInactivePlayer() {
        return firstPlayerIsActive ? secondPlayer : firstPlayer;
    }

    @Override
    public MutablePlayer getOtherPlayer(User user) {
        if (firstPlayer.getUser().equals(user)) {
            return secondPlayer;
        }
        if (secondPlayer.getUser().equals(user)) {
            return firstPlayer;
        }
        throw new IllegalArgumentException(String.format("User %s is not playing this match", user));
    }

    public MutablePlayer getPlayerAsMutable(Player player) {
        if (player.getUser().equals(firstPlayer.getUser())) {
            return firstPlayer;
        }
        return secondPlayer;
    }

    public MutableStructure getStructureAsMutable(Structure structure) {
        return Stream.of(firstPlayer.getStructuresAsMutable(), secondPlayer.getStructuresAsMutable())
                .flatMap(Structures::stream)
                .filter(mutableStructure -> structure.getEntityId().equals(mutableStructure.getEntityId())).findFirst()
                .orElseThrow();
    }

    @Override
    public Collection<Structure> getAllStructures() {
        return Stream.of(firstPlayer.getStructuresAsMutable(), secondPlayer.getStructuresAsMutable())
                .flatMap(Structures::stream).collect(toUnmodifiableList());
    }

    public ModificationComputer getModificationComputer() {
        return modificationComputer;
    }

    public boolean isOver() {
        return isOver;
    }

    public MutablePlayer getWinner() {
        return winner;
    }

    public void start() {
        initialise();
        firstPlayerIsActive = nextBoolean();
        getController(MatchStateController.class).startMatch();
    }

    public void advanceToNextTurn() {
        firstPlayerIsActive = !firstPlayerIsActive;
        currentTurn = new MutableTurn();
    }

    public void end() {
        isOver = true;
        if (getInactivePlayer().getHealth() <= 0) {
            winner = getActivePlayer();
        } else {
            winner = getInactivePlayer();
        }
    }

    private void initialise() {
        List.of(new CardFlowController(this, cardImplFactory), new MatchStateController(this),
                new HealthController(this), new StructureHealthController(this),
                new StructureStateController(this, structureFactory), new TurnController(this))
                .forEach(this::putController);
        controllers.values().forEach(Controller::initialise);
        new TurnTimer(this.eventBus);
    }

    private void putController(Controller controller) {
        this.controllers.put(controller.getClass(), controller);
    }

    @Override
    public String toString() {
        return getActivePlayer().getUser() + " (" + getActivePlayer().getHealth() + ") vs "
                + getInactivePlayer().getUser() + " (" + getInactivePlayer().getHealth() + ")\n"
                + currentTurn.getMoney() + " money, " + currentTurn.getPlayedCards() + " played\n"
                + getActivePlayer().getDeckAndDiscardPile().getDeck().size() + " in deck, "
                + getActivePlayer().getDeckAndDiscardPile().getDiscardPile() + " in discard\n" + "active structures: "
                + getActivePlayer().getStructuresAsMutable().toString() + "\n" + "inactive structures: "
                + getInactivePlayer().getStructuresAsMutable().toString();
    }
}
