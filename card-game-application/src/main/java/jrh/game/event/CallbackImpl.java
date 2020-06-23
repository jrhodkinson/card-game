package jrh.game.event;

import jrh.game.api.Callback;
import jrh.game.api.event.Event;
import jrh.game.api.EventBus;
import jrh.game.common.EventHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CallbackImpl implements Callback {

    private static final Logger logger = LogManager.getLogger(CallbackImpl.class);

    private final EventBus eventBus;
    private boolean dirty = false;

    private final Queue<Event> enqueuedEvents = new LinkedList<>();
    private final List<EventHandler> registeredEventHandlers = new ArrayList<>();
    private final List<EventHandler> unregisteredEventHandlers = new ArrayList<>();

    CallbackImpl(EventBus eventBus) {
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

    @Override
    public void resolve(Event event) {
        eventBus.dispatch(event);
    }

    @Override
    public void enqueue(Event event) {
        logger.debug("Enqueueing event={}", event);
        dirty = true;
        enqueuedEvents.add(event);
    }

    @Override
    public void register(EventHandler eventHandler) {
        logger.debug("Will register new event handler={}", eventHandler);
        dirty = true;
        registeredEventHandlers.add(eventHandler);
    }

    @Override
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
