package jrh.game.api;

public interface ControllableMatch extends Match {

    <T extends Controller> boolean hasController(Class<T> controllerClass);

    <T extends Controller> T getController(Class<T> controllerClass);
}
