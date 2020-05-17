package jrh.game.event.bus;

import jrh.game.event.Event;
import jrh.game.event.EventHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class EventBus {

    private static final Logger logger = LogManager.getLogger(EventBus.class);

    private final List<Event> events = new ArrayList<>();
    private final SubscriberRegistry subscriberRegistry = new SubscriberRegistry();

    public void register(EventHandler eventHandler) {
        subscriberRegistry.register(eventHandler);
    }

    public void dispatch(Event event) {
        logger.debug("Dispatching event={}", event);
        List<Subscriber> subscribers = subscriberRegistry.getSubscribers(event.getClass());
        subscribers.forEach(subscriber -> subscriber.dispatch(event, new Callback()));
    }

}
