package jrh.game.api;

import jrh.game.common.description.AtomicDescription;
import jrh.game.common.EventHandler;

public interface Behaviour extends EventHandler {

    boolean requiresDamageableTarget();

    boolean requiresStoreTarget();

    AtomicDescription getDescription();
}
