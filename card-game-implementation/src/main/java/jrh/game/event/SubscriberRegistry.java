package jrh.game.event;

import jrh.game.api.Callback;
import jrh.game.api.Event;
import jrh.game.api.Match;
import jrh.game.api.Subscribe;
import jrh.game.api.SubscribeAll;
import jrh.game.common.EventHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

public class SubscriberRegistry {

    private static final Logger logger = LogManager.getLogger(SubscriberRegistry.class);

    private final List<Subscriber> subscribers = new ArrayList<>();

    SubscriberRegistry() {

    }

    @SuppressWarnings("unchecked")
    void register(EventHandler eventHandler) {
        logger.info("Registering event handler of type={}", eventHandler.getClass().getSimpleName());
        Method[] allMethods = eventHandler.getClass().getDeclaredMethods();
        for (Method method : allMethods) {
            if (method.isAnnotationPresent(Subscribe.class) && method.isAnnotationPresent(SubscribeAll.class)) {
                throw new EventBusException(
                        "Method must only be annotated with one of @Subscribe or @SubscribeAll, not both");
            }
            if (method.isAnnotationPresent(Subscribe.class)) {
                verifyTypeSignature(method, Subscribe.class);
                Subscriber subscriber = new Subscriber(eventHandler, method,
                        singletonList((Class<? extends Event>) method.getParameterTypes()[0]));
                logger.info("Registering subscriber={}", subscriber);
                subscribers.add(subscriber);
            }
            if (method.isAnnotationPresent(SubscribeAll.class)) {
                verifyTypeSignature(method, SubscribeAll.class);
                List<Class<? extends Event>> eventTypes = Arrays
                        .asList(method.getAnnotation(SubscribeAll.class).value());
                Subscriber subscriber = new Subscriber(eventHandler, method, eventTypes);
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
        return subscribers.stream().filter(subscriber -> subscriber.listensTo(eventType)).collect(Collectors.toList());
    }

    private void verifyTypeSignature(Method method, Class<?> annotationClass) {
        if (method.getParameterCount() == 0 || method.getParameterCount() > 3) {
            throw new EventBusException("Subscriber must have either between one and three (inclusive) parameters");
        }
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (Subscribe.class.equals(annotationClass) && !Event.class.isAssignableFrom(parameterTypes[0])) {
            throw new EventBusException("Event must be assignable from the type of Subscriber's first parameter");
        } else if (SubscribeAll.class.equals(annotationClass) && !Event.class.equals(parameterTypes[0])) {
            throw new EventBusException("Event must be the type of Subscriber's first parameter");
        }
        if (parameterTypes.length > 1 && !Match.class.isAssignableFrom(parameterTypes[1])) {
            throw new EventBusException("Second parameter of Subscriber, if present, must be of type Match");
        }
        if (parameterTypes.length == 3 && !Callback.class.isAssignableFrom(parameterTypes[2])) {
            throw new EventBusException("Third parameter of Subscriber, if present, must be of type Callback");
        }
    }
}
