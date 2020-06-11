package jrh.game.event;

import jrh.game.api.Match;
import jrh.game.common.event.Event;
import jrh.game.common.event.EventHandler;
import jrh.game.common.event.Subscribe;
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
        for (Method method : allMethods) {
            if (method.isAnnotationPresent(Subscribe.class)) {
                verifyTypeSignature(method);
                Subscriber subscriber = new Subscriber(eventHandler, method);
                logger.info("Registering subscriber={}", subscriber);
                subscribers.add(subscriber);
            }
        }
    }

    void unregister(EventHandler eventHandler) {
        logger.info("Unregistering event handler of type={}", eventHandler.getClass().getSimpleName());
        subscribers.removeIf(subscriber -> {
            if (subscriber.getHandler().equals(eventHandler)) {
                logger.info("Unregistering subscriber={}", subscriber);
                return true;
            }
            return false;
        });
    }

    List<Subscriber> getSubscribers(Class<? extends Event> eventType) {
        return subscribers.stream().filter(subscriber -> subscriber.getEventType().isAssignableFrom(eventType))
                .collect(Collectors.toList());
    }

    private void verifyTypeSignature(Method method) {
        if (method.getParameterCount() == 0 || method.getParameterCount() > 3) {
            throw new EventBusException("Subscriber must have either between one and three (inclusive) parameters");
        }
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (!Event.class.isAssignableFrom(parameterTypes[0])) {
            throw new EventBusException("Event must be assignable from Subscriber's first parameter");
        }
        if (parameterTypes.length > 1 && !Match.class.equals(parameterTypes[1])) {
            throw new EventBusException("Second parameter of Subscriber, if present, must be of type MatchControllers");
        }
        if (parameterTypes.length == 3 && !CallbackImpl.class.equals(parameterTypes[2])) {
            throw new EventBusException("Third parameter of Subscriber, if present, must be of type Callback");
        }
    }
}
