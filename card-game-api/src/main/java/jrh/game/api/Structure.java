package jrh.game.api;

import jrh.game.common.EntityId;
import jrh.game.common.StructureId;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface Structure extends Damageable {

    EntityId getEntityId();

    StructureId getStructureId();

    String getName();

    Optional<String> getFlavorText();

    int getHealth();

    boolean hasPower(Class<? extends Power> powerClass);

    Collection<Power> getAllPowers();

    List<Power> getPowers(Class<? extends Power> powerClass);
}
