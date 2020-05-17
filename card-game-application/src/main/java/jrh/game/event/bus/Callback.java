package jrh.game.event.bus;

import jrh.game.event.Event;

import java.util.LinkedList;
import java.util.Queue;

public class Callback {

    private final Queue<Event> enqueuedEvents = new LinkedList<>();
    private final EventBus eventBus;
    private boolean dirty = false;

    Callback(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void resolve(Event event) {
        eventBus.dispatch(event);
    }

    public void enqueue(Event event) {
        this.dirty = true;
        this.enqueuedEvents.add(event);
    }

    void reset() {
        this.enqueuedEvents.clear();
        this.dirty = false;
    }

    boolean isDirty() {
        return dirty;
    }

    Queue<Event> getEnqueuedEvents() {
        return this.enqueuedEvents;
    }

}
