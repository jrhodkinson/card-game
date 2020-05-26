package jrh.game.event;

public interface EventHandler {

    default void registerWith(EventBus eventBus) {
        eventBus.register(this);
    }

    default void unregisterWith(EventBus eventBus) {
        eventBus.unregister(this);
    }
}
