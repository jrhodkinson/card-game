package jrh.game.event.bus;

import jrh.game.event.Event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SubscriberRegistry {

    private final List<Subscriber> subscribers = new ArrayList<>();

    SubscriberRegistry() {

    }

    void register(Object listener) {
        Method[] allMethods = listener.getClass().getMethods();
        for (Method method : allMethods){
            if (method.isAnnotationPresent(Subscribe.class)) {
                if (method.getParameterCount() == 0 || method.getParameterCount() > 2) {
                    throw new EventBusException("Subscriber must have either one or two parameters");
                }
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (!Event.class.isAssignableFrom(parameterTypes[0])) {
                    throw new EventBusException("Event must be assignable from Subscriber's first parameter");
                }
                if (parameterTypes.length == 2 && !Callback.class.equals(parameterTypes[1])) {
                    throw new EventBusException("Second parameter of Subscriber, if present, must be Callback");
                }
                subscribers.add(new Subscriber(listener, method));
            }
        }
    }

    List<Subscriber> getSubscribers(Class<? extends Event> eventType) {
        return subscribers.stream()
                .filter(subscriber -> subscriber.getEventType().isAssignableFrom(eventType))
                .collect(Collectors.toList());
    }
}
