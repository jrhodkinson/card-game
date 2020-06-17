package jrh.game.api;

import java.util.Collection;

public interface Match {

    <T extends Controller> boolean hasController(Class<T> controllerClass);

    <T extends Controller> T getController(Class<T> controllerClass);

    Player getActivePlayer();

    Player getInactivePlayer();

    Player getOtherPlayer(Player player);

    Turn getCurrentTurn();

    Collection<Structure> getAllStructures();

    Store getStore();
}
