package jrh.game.event;

import jrh.game.api.Callback;
import jrh.game.api.Event;
import jrh.game.api.Match;
import jrh.game.common.EventHandler;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Subscriber {

    private static final Logger logger = LogManager.getLogger(Subscriber.class);

    private final EventHandler handler;
    private final Method method;
    private final List<Class<? extends Event>> eventTypes;
    private final int parameterCount;

    Subscriber(EventHandler handler, Method method, List<Class<? extends Event>> eventTypes) {
        this.handler = handler;
        this.method = method;
        this.eventTypes = eventTypes;
        this.parameterCount = method.getParameterCount();
        method.setAccessible(true);
    }

    boolean listensTo(Class<? extends Event> eventType) {
        return eventTypes.stream().anyMatch(t -> t.isAssignableFrom(eventType));
    }

    EventHandler getHandler() {
        return handler;
    }

    void dispatch(Event event, Match match, Callback callback) {
        try {
            if (parameterCount == 3) {
                method.invoke(handler, event, match, callback);
            } else if (parameterCount == 2) {
                method.invoke(handler, event, match);
            } else if (parameterCount == 1) {
                method.invoke(handler, event);
            }
        } catch (IllegalAccessException e) {
            // This shouldn't be able to happen
            logger.error("IllegalAccessException trying to access method={}", method, e);
        } catch (InvocationTargetException e) {
            logger.error("InvocationTargetException invoking method={}", method, e.getCause());
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("method", method)
                .append("eventTypes", eventTypes).append("parameterCount", parameterCount).toString();
    }
}
