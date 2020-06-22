package jrh.game.api;

import jrh.game.common.CardDescription;
import jrh.game.common.CardId;
import jrh.game.common.Color;
import jrh.game.common.InstanceId;

import java.util.Collection;

public interface Card {

    InstanceId getInstanceId();

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
