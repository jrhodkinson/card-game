package jrh.game.common.event;

public interface Callback {

    void resolve(Event event);

    void enqueue(Event event);

    void register(EventHandler eventHandler);

    void unregister(EventHandler eventHandler);
}
