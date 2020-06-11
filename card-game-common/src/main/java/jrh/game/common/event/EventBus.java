package jrh.game.common.event;

public interface EventBus {

    void register(EventHandler eventHandler);

    void unregister(EventHandler eventHandler);

    void dispatch(Event initialEvent);
}
