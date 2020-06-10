package jrh.game.event;

import jrh.game.common.Event;
import jrh.game.common.EventHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Callback {

    private static final Logger logger = LogManager.getLogger(Callback.class);

    private final EventBus eventBus;
    private boolean dirty = false;

    private final Queue<Event> enqueuedEvents = new LinkedList<>();
    private final List<EventHandler> registeredEventHandlers = new ArrayList<>();
    private final List<EventHandler> unregisteredEventHandlers = new ArrayList<>();

    Callback(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    boolean isDirty() {
        return dirty;
    }

    void reset() {
        enqueuedEvents.clear();
        registeredEventHandlers.clear();
        unregisteredEventHandlers.clear();
        dirty = false;
    }

    public void resolve(Event event) {
        eventBus.dispatch(event);
    }

    public void enqueue(Event event) {
        logger.debug("Enqueueing event={}", event);
        dirty = true;
        enqueuedEvents.add(event);
    }

    public void register(EventHandler eventHandler) {
        logger.debug("Will register new event handler={}", eventHandler);
        dirty = true;
        registeredEventHandlers.add(eventHandler);
    }

    public void unregister(EventHandler eventHandler) {
        logger.debug("Will unregister event handler={}", eventHandler);
        dirty = true;
        unregisteredEventHandlers.add(eventHandler);
    }

    Queue<Event> getEnqueuedEvents() {
        return enqueuedEvents;
    }

    List<EventHandler> getRegisteredEventHandlers() {
        return registeredEventHandlers;
    }

    List<EventHandler> getUnregisteredEventHandlers() {
        return unregisteredEventHandlers;
    }
}
