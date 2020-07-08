package jrh.game.event;

import jrh.game.api.Callback;
import jrh.game.api.Event;
import jrh.game.api.Match;
import jrh.game.api.Subscribe;
import jrh.game.api.SubscribeAll;
import jrh.game.common.EventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(MockitoExtension.class)
public class SingleMatchEventBusTest {

    private static final String FIRST_HANDLER = "firstTestEventHandler";
    private static final String SECOND_HANDLER = "secondTestEventHandler";
    private static final String ALL_HANDLER = "allTestEventsHandler";

    @Mock
    private Match match;

    private SingleMatchEventBus eventBus;
    private TestHandler handler;

    @BeforeEach
    public void setUp() {
        eventBus = new SingleMatchEventBus(match);
        handler = new TestHandler();
        eventBus.register(handler);
    }

    @Test
    public void callsMethodWhenEventOccurs() {
        FirstTestEvent event = new FirstTestEvent();
        eventBus.dispatch(event);
        assertThat(handler.methodCalls.get(FIRST_HANDLER), hasSize(1));
        MethodCall call = handler.methodCalls.get(FIRST_HANDLER).get(0);
        assertThat(call.event, equalTo(event));
        assertThat(call.match, equalTo(match));
    }

    @Test
    public void callbackResolvesAdditionalEvent() {
        SecondTestEvent event = new SecondTestEvent(true);
        eventBus.dispatch(event);
        assertThat(handler.methodCalls.get(SECOND_HANDLER), hasSize(1));
        assertThat(handler.methodCalls.get(FIRST_HANDLER), hasSize(1));
    }

    @Test
    public void subscribeAllCallsMethodWithRelevantEvent() {
        FirstTestEvent firstEvent = new FirstTestEvent();
        eventBus.dispatch(firstEvent);
        assertThat(handler.methodCalls.get(ALL_HANDLER), hasSize(1));
        assertThat(handler.methodCalls.get(ALL_HANDLER).get(0).event, equalTo(firstEvent));

        SecondTestEvent secondTestEvent = new SecondTestEvent(false);
        eventBus.dispatch(secondTestEvent);
        assertThat(handler.methodCalls.get(ALL_HANDLER), hasSize(2));
        assertThat(handler.methodCalls.get(ALL_HANDLER).get(1).event, equalTo(secondTestEvent));
    }

    private static class TestHandler implements EventHandler {

        private final Map<String, List<MethodCall>> methodCalls = new HashMap<>();

        @Subscribe
        private void firstTestEvent(FirstTestEvent firstTestEvent, Match match) {
            addMethodCall(FIRST_HANDLER, firstTestEvent, match);
        }

        @Subscribe
        private void secondTestEvent(SecondTestEvent secondTestEvent, Match match, Callback callback) {
            addMethodCall(SECOND_HANDLER, secondTestEvent, match);
            if (secondTestEvent.enqueue) {
                callback.enqueue(new FirstTestEvent());
            }
        }

        @SubscribeAll({ FirstTestEvent.class, SecondTestEvent.class })
        private void allEvents(Event event, Match match, Callback callback) {
            addMethodCall(ALL_HANDLER, event, match);
        }

        private void addMethodCall(String handler, Event event, Match match) {
            List<MethodCall> methodCallList = methodCalls.getOrDefault(handler, new ArrayList<>());
            methodCallList.add(new MethodCall(event, match));
            methodCalls.put(handler, methodCallList);
        }
    }

    private static class MethodCall {
        private final Event event;
        private final Match match;

        public MethodCall(Event event, Match match) {
            this.event = event;
            this.match = match;
        }
    }

    private static class FirstTestEvent implements Event {

    }

    private static class SecondTestEvent implements Event {
        private final boolean enqueue;

        public SecondTestEvent(boolean enqueue) {
            this.enqueue = enqueue;
        }
    }
}
