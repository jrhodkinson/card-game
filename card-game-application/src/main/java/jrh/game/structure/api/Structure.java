package jrh.game.structure.api;

import jrh.game.match.api.Damageable;
import jrh.game.structure.StructureId;
import jrh.game.structure.power.Power;

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
