package jrh.game.api;

import jrh.game.common.StructureId;

import java.util.Collection;
import java.util.UUID;

public interface Structure extends Damageable {

    UUID getInstanceId();

    StructureId getStructureId();

    String getName();

    int getHealth();

    boolean hasPower(Class<? extends Power> powerClass);

    Collection<Power> getPowers();

    Power getPower(Class<? extends Power> powerClass);
}
