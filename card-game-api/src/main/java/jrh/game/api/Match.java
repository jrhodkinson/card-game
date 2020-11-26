package jrh.game.api;

import jrh.game.common.User;

import java.util.Collection;
import java.util.UUID;

public interface Match {

    UUID getId();

    ActionHandler getActionHandler();

    <T extends Controller> boolean hasController(Class<T> controllerClass);

    <T extends Controller> T getController(Class<T> controllerClass);

    Player getPlayer(User user);

    Player getActivePlayer();

    Player getInactivePlayer();

    Player getOtherPlayer(User user);

    Turn getCurrentTurn();

    Collection<Structure> getAllStructures();

    Store getStore();
}
