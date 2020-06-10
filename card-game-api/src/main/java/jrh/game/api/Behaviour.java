package jrh.game.api;

import jrh.game.common.BehaviourDescription;
import jrh.game.common.EventHandler;

public interface Behaviour extends EventHandler {

    boolean requiresTarget();

    BehaviourDescription getDescription();
}
