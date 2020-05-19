package jrh.game.event;

import jrh.game.event.bus.EventBus;

public interface EventHandler {

    default void registerWith(EventBus eventBus) {
        eventBus.register(this);
    }

    default void unregisterWith(EventBus eventBus) {
        eventBus.unregister(this);
    }
}
