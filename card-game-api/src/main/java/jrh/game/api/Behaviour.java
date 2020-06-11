package jrh.game.api;

import jrh.game.common.BehaviourDescription;
import jrh.game.common.event.EventHandler;

public interface Behaviour extends EventHandler {

    boolean requiresTarget();

    BehaviourDescription getDescription();
}
