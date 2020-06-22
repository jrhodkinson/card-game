package jrh.game.api;

import jrh.game.common.InstanceId;
import jrh.game.common.StructureId;

import java.util.Collection;

public interface Structure extends Damageable {

    InstanceId getInstanceId();

    StructureId getStructureId();

    String getName();

    int getHealth();

    boolean hasPower(Class<? extends Power> powerClass);

    Collection<Power> getPowers();

    Power getPower(Class<? extends Power> powerClass);
}
