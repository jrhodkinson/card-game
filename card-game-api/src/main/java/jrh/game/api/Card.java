package jrh.game.api;

import jrh.game.common.CardDescription;
import jrh.game.common.CardId;
import jrh.game.common.Color;
import jrh.game.common.EntityId;

import java.util.Collection;
import java.util.Optional;

public interface Card {

    EntityId getEntityId();

    CardId getCardId();

    String getName();

    Optional<String> getFlavorText();

    int getCost();

    Color getColor();

    CardDescription getDescription();

    boolean hasBehaviour(Class<? extends Behaviour> behaviourClass);

    Collection<Behaviour> getBehaviours();

    Behaviour getBehaviour(Class<? extends Behaviour> behaviourClass);

    boolean requiresTarget();
}
