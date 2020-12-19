package jrh.game.match;

import jrh.game.api.EventBus;
import jrh.game.api.Subscribe;
import jrh.game.api.event.PlayerRanOutOfTime;
import jrh.game.api.event.TurnEnded;
import jrh.game.api.event.TurnStarted;
import jrh.game.api.event.TurnWillEndAt;
import jrh.game.common.EventHandler;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TurnTimer implements EventHandler {

    private final int TURN_TIMEOUT_IN_SECONDS = 75;

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    private final EventBus eventBus;
    private TurnWillEndAt turnWillEndAt;
    private Future<?> reminder;
    private Future<?> forceEndTurn;

    public TurnTimer(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    @Subscribe
    public void turnStarted(TurnStarted turnStarted) {
        cancel();
        TurnWillEndAt turnWillEndAt = new TurnWillEndAt(Instant.now().plusSeconds(TURN_TIMEOUT_IN_SECONDS));
        eventBus.dispatch(turnWillEndAt);
        reminder = scheduledExecutorService.schedule(() -> eventBus.dispatch(turnWillEndAt),
                TURN_TIMEOUT_IN_SECONDS - 20, TimeUnit.SECONDS);
        forceEndTurn = scheduledExecutorService.schedule(
                () -> eventBus.dispatch(new PlayerRanOutOfTime(turnStarted.getNewPlayer())), TURN_TIMEOUT_IN_SECONDS,
                TimeUnit.SECONDS);
    }

    @Subscribe
    public void turnEnded(TurnEnded turnEnded) {
        cancel();
    }

    private void cancel() {
        if (reminder != null) {
            reminder.cancel(false);
        }
        if (forceEndTurn != null) {
            forceEndTurn.cancel(false);
        }
    }
}
