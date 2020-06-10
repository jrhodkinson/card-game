package jrh.game.event;

import jrh.game.common.EventHandler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Callback {

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
        dirty = true;
        enqueuedEvents.add(event);
    }

    public void register(EventHandler eventHandler) {
        dirty = true;
        registeredEventHandlers.add(eventHandler);
    }

    public void unregister(EventHandler eventHandler) {
        dirty = true;
        unregisteredEventHandlers.add(eventHandler);
    }

    Queue<Event> getEnqueuedEvents() {
        return enqueuedEvents;
    }

    List<EventHandler> getRegisteredEventHandlers() {
        return unregisteredEventHandlers;
    }

    List<EventHandler> getUnregisteredEventHandlers() {
        return unregisteredEventHandlers;
    }
}
