package jrh.game.api;

import jrh.game.common.User;

import java.util.Collection;

public interface Match {

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
