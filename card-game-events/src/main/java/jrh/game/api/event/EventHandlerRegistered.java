package jrh.game.api.event;

import jrh.game.api.Event;
import jrh.game.common.EventHandler;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class EventHandlerRegistered implements Event {

    private final Class<? extends EventHandler> eventHandlerClass;

    public EventHandlerRegistered(Class<? extends EventHandler> eventHandlerClass) {
        this.eventHandlerClass = eventHandlerClass;
    }

    public Class<? extends EventHandler> getEventHandlerClass() {
        return eventHandlerClass;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("eventHandlerClass", eventHandlerClass)
            .toString();
    }
}
