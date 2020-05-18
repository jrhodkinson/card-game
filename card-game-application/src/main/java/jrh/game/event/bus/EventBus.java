package jrh.game.event.bus;

import jrh.game.event.Event;
import jrh.game.event.EventHandler;
import jrh.game.match.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EventBus {

    private static final Logger logger = LogManager.getLogger(EventBus.class);

    private final SubscriberRegistry subscriberRegistry = new SubscriberRegistry();
    private final Match match;
    private final Callback callback;

    public EventBus(Match match) {
        this.match = match;
        this.callback = new Callback(this);
    }

    public void register(EventHandler eventHandler) {
        subscriberRegistry.register(eventHandler);
    }

    public void unregister(EventHandler eventHandler) {
        subscriberRegistry.unregister(eventHandler);
    }

    public void dispatch(Event initialEvent) {
        Queue<Event> events = new LinkedList<>();
        events.add(initialEvent);
        while (events.size() > 0) {
            Event event = events.poll();
            logger.debug("Processing event={}", event);
            List<Subscriber> subscribers = subscriberRegistry.getSubscribers(event.getClass());
            for (Subscriber subscriber : subscribers) {
                logger.debug("TX event={} to subscriber of type={}", event, subscriber.getHandler().getClass());
                subscriber.dispatch(event, match, callback);
                if (callback.isDirty()) {
                    events.addAll(callback.getEnqueuedEvents());
                    if (callback.didUnregister()) {
                        subscriberRegistry.unregister(subscriber.getHandler());
                    }
                    callback.reset();
                }
            }
        }
    }
}
