package jrh.game.event.bus;

import jrh.game.event.Event;
import jrh.game.event.EventHandler;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Subscriber {

    private static final Logger logger = LogManager.getLogger(Subscriber.class);

    private final EventHandler handler;
    private final Method method;
    private final Class<? extends Event> eventType;
    private final boolean canCallback;

    @SuppressWarnings("unchecked")
    Subscriber(EventHandler handler, Method method) {
        this.handler = handler;
        this.method = method;
        this.eventType = (Class<? extends Event>) method.getParameterTypes()[0];
        this.canCallback = method.getParameterCount() == 2;
        method.setAccessible(true);
    }

    public Class<? extends EventHandler> getHandlerClass() {
        return handler.getClass();
    }

    public Class<? extends Event> getEventType() {
        return eventType;
    }

    public void dispatch(Event event, Callback callback) {
        try {
            if (canCallback) {
                method.invoke(handler, event, callback);
            } else {
                method.invoke(handler, event);
            }
        } catch (IllegalAccessException e) {
            // This shouldn't be able to happen
            logger.error("IllegalAccessException trying to access method={}", method, e);
        } catch (InvocationTargetException e) {
            logger.error("InvocationTargetException in method={} causedBy={}", method, e.getCause(), e);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("method", method)
                .append("eventType", eventType)
                .append("canCallback", canCallback)
                .toString();
    }
}
