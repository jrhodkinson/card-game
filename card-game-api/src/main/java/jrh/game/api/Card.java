package jrh.game.api;

import jrh.game.common.CardDescription;
import jrh.game.common.CardId;
import jrh.game.common.Color;

import java.util.Collection;
import java.util.UUID;

public interface Card {

    UUID getInstanceId();

    CardId getCardId();

    String getName();

    int getCost();

    Color getColor();

    CardDescription getDescription();

    boolean hasBehaviour(Class<? extends Behaviour> behaviourClass);

    Collection<Behaviour> getBehaviours();

    Behaviour getBehaviour(Class<? extends Behaviour> behaviourClass);

    boolean requiresTarget();
}
