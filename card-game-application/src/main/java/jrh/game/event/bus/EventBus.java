package jrh.game.event.bus;

import jrh.game.event.Event;

import java.util.ArrayList;
import java.util.List;

public class EventBus {

    private final List<Event> events = new ArrayList<>();
    private final SubscriberRegistry subscriberRegistry = new SubscriberRegistry();

    public void register(Object listener) {
        subscriberRegistry.register(listener);
    }

    public void dispatch(Event event) {
        List<Subscriber> subscribers = subscriberRegistry.getSubscribers(event.getClass());
        subscribers.forEach(subscriber -> subscriber.dispatch(event, new Callback()));
    }

}
