package jrh.game.api.event;

import jrh.game.common.EventHandler;

public interface EventBus {

    void register(EventHandler eventHandler);

    void unregister(EventHandler eventHandler);

    void dispatch(Event initialEvent);
}
