package jrh.game.api;

import java.util.Collection;

public interface Match {

    MatchActionHandler getActionHandler();

    Player getActivePlayer();

    Player getInactivePlayer();

    Player getOtherPlayer(Player player);

    Turn getCurrentTurn();

    Collection<Structure> getAllStructures();

    Store getStore();
}
