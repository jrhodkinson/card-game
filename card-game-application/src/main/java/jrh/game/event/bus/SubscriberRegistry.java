package jrh.game.event.bus;

import jrh.game.event.Event;
import jrh.game.event.EventHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SubscriberRegistry {

    private static final Logger logger = LogManager.getLogger(SubscriberRegistry.class);

    private final List<Subscriber> subscribers = new ArrayList<>();

    SubscriberRegistry() {

    }

    void register(EventHandler eventHandler) {
        logger.info("Registering event handler of type={}", eventHandler.getClass().getSimpleName());
        Method[] allMethods = eventHandler.getClass().getDeclaredMethods();
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
                Subscriber subscriber = new Subscriber(eventHandler, method);
                logger.info("Registering subscriber={}", subscriber);
                subscribers.add(subscriber);
            }
        }
    }

    List<Subscriber> getSubscribers(Class<? extends Event> eventType) {
        return subscribers.stream()
                .filter(subscriber -> subscriber.getEventType().isAssignableFrom(eventType))
                .collect(Collectors.toList());
    }
}
