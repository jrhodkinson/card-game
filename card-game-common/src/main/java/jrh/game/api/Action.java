package jrh.game.api;

import jrh.game.common.User;

public interface Action extends Event {
    User getActor();
}
