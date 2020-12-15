package jrh.game.api;

import jrh.game.common.CardId;
import jrh.game.common.EntityId;
import jrh.game.common.description.Description;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface Card {

    EntityId getEntityId();

    CardId getCardId();

    String getName();

    Optional<String> getFlavorText();

    int getCost();

    Description getDescription();

    boolean hasBehaviour(Class<? extends Behaviour> behaviourClass);

    Collection<Behaviour> getAllBehaviours();

    List<Behaviour> getBehaviours(Class<? extends Behaviour> behaviourClass);

    boolean requiresDamageableTarget();

    boolean requiresStoreTarget();

    boolean isPurchasable();

}
