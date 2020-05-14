package jrh.game.event.bus;

import jrh.game.event.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Subscriber {

    private static final Logger logger = LogManager.getLogger(Subscriber.class);

    private final Object listener;
    private final Method method;
    private final Class<? extends Event> eventType;
    private final boolean canCallback;

    @SuppressWarnings("unchecked")
    Subscriber(Object listener, Method method) {
        this.listener = listener;
        this.method = method;
        this.eventType = (Class<? extends Event>) method.getParameterTypes()[0];
        this.canCallback = method.getParameterCount() == 2;
        method.setAccessible(true);
    }

    public Class<? extends Event> getEventType() {
        return eventType;
    }

    public void dispatch(Event event, Callback callback) {
        try {
            if (canCallback) {
                method.invoke(listener, event, callback);
            } else {
                method.invoke(listener, event);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error(e);
        }
    }
}
