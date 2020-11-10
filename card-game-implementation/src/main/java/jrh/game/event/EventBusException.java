package jrh.game.event;

public class EventBusException extends RuntimeException {

    EventBusException(String message) {
        super(message);
    }

    EventBusException(String message, Throwable cause) {
        super(message, cause);
    }
}
