package jrh.game.service.lobby;

import jrh.game.alert.AlertService;
import jrh.game.common.account.AccountId;
import jrh.game.service.account.Accounts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SlowQueueAlerter {

    private static final Logger logger = LogManager.getLogger(SlowQueueAlerter.class);

    private static final long POLL_MS = 5_000;
    private static final Duration DURATION_BEFORE_ALERTING = Duration.ofSeconds(30);
    private static final Duration DURATION_BETWEEN_ALERTS = Duration.ofMinutes(30);

    private final AlertService alertService;
    private final MatchQueue matchQueue;
    private final String channel;
    private final Accounts accounts;
    private final String website;
    private Instant queueHasBeenPopulatedSince = null;
    private Instant lastAlert = Instant.EPOCH;

    public SlowQueueAlerter(AlertService alertService, MatchQueue matchQueue, String channel, Accounts accounts,
            String website) {
        this.alertService = alertService;
        this.matchQueue = matchQueue;
        this.channel = channel;
        this.accounts = accounts;
        this.website = website;
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(this::run, 0, POLL_MS, TimeUnit.MILLISECONDS);
    }

    private void run() {
        if (matchQueue.size() > 0) {
            if (queueHasBeenPopulatedSince == null) {
                queueHasBeenPopulatedSince = Instant.now();
            } else if (queueHasBeenPopulatedSince.plus(DURATION_BEFORE_ALERTING).isBefore(Instant.now())) {
                if (lastAlert.plus(DURATION_BETWEEN_ALERTS).isBefore(Instant.now())) {
                    sendAlert(matchQueue.peek());
                } else {
                    logger.info("Not alerting, too soon");
                }
            }
        } else {
            queueHasBeenPopulatedSince = null;
        }
    }

    private void sendAlert(AccountId accountId) {
        if (accountId == null) {
            logger.info("Not alerting, queue is now empty");
            return;
        }
        lastAlert = Instant.now();
        String name = accounts.getAccount(accountId).getName();
        String message = String
            .format("**%s** is waiting for a game, does anybody want to join them? %s", name, website);
        alertService.sendAlert(channel, message);
    }
}
