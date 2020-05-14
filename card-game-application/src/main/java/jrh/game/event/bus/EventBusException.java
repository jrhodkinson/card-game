package jrh.game.event.bus;

public class EventBusException extends RuntimeException {

    EventBusException(String message) {
        super(message);
    }

    EventBusException(String message, Throwable cause) {
        super(message, cause);
    }
}
