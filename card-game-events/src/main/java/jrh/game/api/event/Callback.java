package jrh.game.api.event;

import jrh.game.common.EventHandler;

public interface Callback {

    void resolve(Event event);

    void enqueue(Event event);

    void register(EventHandler eventHandler);

    void unregister(EventHandler eventHandler);
}
