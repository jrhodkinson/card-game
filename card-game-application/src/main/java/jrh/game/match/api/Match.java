package jrh.game.match.api;

import jrh.game.event.EventBus;
import jrh.game.match.Controller;
import jrh.game.match.ModificationComputer;
import jrh.game.structure.api.Structure;

import java.util.Collection;

public interface Match {

    EventBus getEventBus();

    <T extends Controller> boolean hasController(Class<T> controllerClass);

    <T extends Controller> T getController(Class<T> controllerClass);

    Player getActivePlayer();

    Player getInactivePlayer();

    Player getOtherPlayer(Player player);

    Turn getCurrentTurn();

    Collection<Structure> getAllStructures();

    ModificationComputer getModificationComputer();
}
