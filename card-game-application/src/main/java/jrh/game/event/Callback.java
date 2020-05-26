package jrh.game.event;

import java.util.LinkedList;
import java.util.Queue;

public class Callback {

    private final EventBus eventBus;
    private boolean dirty = false;

    private final Queue<Event> enqueuedEvents = new LinkedList<>();
    private boolean unregistered = false;

    Callback(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    boolean isDirty() {
        return dirty;
    }

    void reset() {
        this.enqueuedEvents.clear();
        this.unregistered = false;
        this.dirty = false;
    }

    public void resolve(Event event) {
        eventBus.dispatch(event);
    }

    public void enqueue(Event event) {
        this.dirty = true;
        this.enqueuedEvents.add(event);
    }

    public void unregister() {
        this.dirty = true;
        this.unregistered = true;
    }

    Queue<Event> getEnqueuedEvents() {
        return this.enqueuedEvents;
    }

    boolean didUnregister() {
        return unregistered;
    }

}
